package de.cherry.workbench.clazz;

import de.cherry.workbench.self.TempProject;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLambdaImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtReturnImpl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public interface ClazzManager {

  TempProject project = TempProject.getInstance();

  String getClazzName();

  default MasterClazz createClazz(File f) {
    try {
      MasterClazz clazz = createClazz(project.getCtClass(f));
      clazz.setFile(f);
      return clazz;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  MasterClazz createClazz(CtClass aClass);

  default boolean detect(File f) {
    CtClass aClass = null;
    try {
      aClass = project.getCtClass(f);
    } catch (ClassNotFoundException e) {
      return false;
    }
    if (aClass == null) return false;
    return detect(aClass);
  }


  boolean detect(CtClass aClass);

  default boolean implementsOrLamda(CtClass aClass, String interfaceName) {
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
}
