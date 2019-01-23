package de.cherry.workbench.general.server;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.cherry.workbench.general.Dir;
import de.cherry.workbench.general.Project;
import de.cherry.workbench.general.spoon.AllClassProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spoon.Launcher;
import spoon.compiler.SpoonResource;
import spoon.compiler.SpoonResourceHelper;
import spoon.processing.Processor;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.factory.Factory;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ApplicationServer {


  public Launcher launcher;
  public Factory factory;

  public AllClassProcessor allSpoonClasses;
  public CtModel model;

  public AtomicReference<Dir> javaDir = new AtomicReference<>();
  public AtomicReference<Dir> webDir = new AtomicReference<>();
  public Pom pom = null;

  public Project project;
  public File location;


  public void init() throws IOException {

    pom = new Pom(project);

    Dir dir = Dir.start(location.toPath());
    dir.add("src",
        src -> src.add("main",
            main -> main.add("java",
                java -> {
                  javaDir.set(java);
                  String packageName = project.group;
                  new File(java.getFile().getAbsolutePath() + File.separator
                      + packageName.replace(".", File.separator)).mkdir();
                  return java;
                })
            ,
            main -> main.add("resources"),
            main -> main.add("webapp",
                webapp -> {
                  webDir.set(webapp);
                  return webapp;
                })
        ),
        src -> src.add("test",
            test -> test.add("java")
        )
    )
        .add(pom)
        .build();

    this.launcher = new Launcher();
    String exactSrc = javaDir.get().getFile().getAbsolutePath();


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

    createSpringMain();

  }

  private void createSpringMain() {

    String application = "Application";
    String args = "args";
    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, args)
        .addStatement("$T.run(" + application + ".class, $L)"
            , org.springframework.boot.SpringApplication.class, args)
        .build();

    TypeSpec helloWorld = TypeSpec.classBuilder(application)
        .addAnnotation(SpringBootApplication.class)
        .addModifiers(Modifier.PUBLIC)
        .addMethod(main)
        .build();

    JavaFile javaFile = JavaFile.builder(this.project.group, helloWorld)
        .build();

    this.addClass(javaFile);
  }

  public void addClass(JavaFile jClass) {
    try {
      File file = new File(javaDir.get().getFile().getAbsolutePath());
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

  public CtClass findClass(Class find) {
    for (CtClass ctClass : allSpoonClasses.getClasses()) {
      if (find.getSimpleName().equals(ctClass.getSimpleName())) {
        return ctClass;
      }
    }
    return null;
  }

  public CtClass findClass(String find) {
    for (CtClass ctClass : allSpoonClasses.getClasses()) {
      if (find.equals(ctClass.getQualifiedName())) {
        return ctClass;
      }
    }
    return null;
  }

  protected ApplicationServer() {

  }

  public void build() {
    File out = javaDir.get().getFile();
    launcher.setSourceOutputDirectory(out);
    launcher.prettyprint();
  }
}
