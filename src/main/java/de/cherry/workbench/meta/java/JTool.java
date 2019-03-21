package de.cherry.workbench.meta.java;

import com.squareup.javapoet.JavaFile;
import de.cherry.workbench.meta.file.Dir;
import spoon.Launcher;
import spoon.compiler.SpoonResource;
import spoon.compiler.SpoonResourceHelper;
import spoon.processing.Processor;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLambdaImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtReturnImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JTool {

  public Launcher launcher;
  public Factory factory;

  public AllClassProcessor allSpoonClasses;
  public CtModel model;
  public Dir javaDir;

  public JTool(Dir javaDir) throws FileNotFoundException {
    this.javaDir = javaDir;
    this.launcher = new Launcher();
    String exactSrc = javaDir.getFile().getAbsolutePath();


    List<SpoonResource> resources =
        SpoonResourceHelper.resources(exactSrc);
    for (SpoonResource resource : resources) {
      launcher.addInputResource(resource);
    }

    launcher.getEnvironment().setAutoImports(true);
    launcher.getEnvironment().setNoClasspath(true);
    launcher.buildModel();

    this.model = launcher.getModel();
    this.factory = launcher.getFactory();


    this.allSpoonClasses = new AllClassProcessor();
    model.processWith(allSpoonClasses);

  }

  public CtClass getCtClass(File f) {
    String name = f.getName();
    if (!name.endsWith(".java"))
      return null;
    String className = name.replace(".java", "");
    for (CtClass aClass : allSpoonClasses.getClasses()) {
      if (className.equals(aClass.getSimpleName()))
        return aClass;

    }
    return null;
  }


  public void addClass(JavaFile jClass) {
    try {
      File file = new File(javaDir.getFile().getAbsolutePath());
      jClass.writeTo(file);
      launcher.addInputResource(SpoonResourceHelper.createResource(file));
      file.delete();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void processWith(Processor<?> processor) {
    model.processWith(processor);
  }

  public CtClass findClass(String find) {
    for (CtClass ctClass : allSpoonClasses.getClasses()) {
      if (find.equals(ctClass.getQualifiedName())) {
        return ctClass;
      }
    }
    return null;
  }



  public void build() {
    File out = javaDir.getFile();
    launcher.setSourceOutputDirectory(out);
    launcher.prettyprint();
  }

  public boolean implementsOrLamda(CtClass aClass, String interfaceName) {
    for (CtTypeReference<?> superInterface : aClass.getSuperInterfaces()) {
      if (superInterface.getQualifiedName().equals(interfaceName)) {
        return true;
      }
    }
    for (Object o : aClass.getMethods()) {
      CtMethod method = (CtMethod) o;
      for (CtStatement statement : method.getBody().getStatements()) {
        Iterable<CtElement> ctElements = Arrays.asList();
        if (statement.getClass().isAssignableFrom(CtLocalVariableImpl.class)) {
          ctElements = ((CtLocalVariableImpl) statement).getDefaultExpression().asIterable();
        }
        if (statement.getClass().isAssignableFrom(CtReturnImpl.class)) {
          ctElements = ((CtReturnImpl) statement).getReturnedExpression().asIterable();
        }
        for (CtElement ctElement : ctElements) {
          if (ctElement.getClass().isAssignableFrom(CtInvocationImpl.class)) {
            CtInvocationImpl invocation = (CtInvocationImpl) ctElement;
            List parameters = invocation.getExecutable().getParameters();
            for (int i = 0; i < parameters.size(); i++) {
              CtTypeReference parameter = (CtTypeReference) parameters.get(i);
              if (parameter.getQualifiedName().equals(interfaceName)
                  && invocation.getArguments().get(i).getClass().isAssignableFrom(CtLambdaImpl.class)) {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  public File findFile(String aClass) {
    CtClass aClass1 = findClass(aClass);
    String qualifiedName = aClass1.getQualifiedName();
    String pathname = javaDir.toString() + qualifiedName.replaceAll(".", System.lineSeparator()) + ".java";
    return new File(pathname);
  }
}
