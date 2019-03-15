package de.cherry.workbench.clazz;

import de.cherry.workbench.TempProject;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtTypeReference;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLambdaImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtReturnImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ClazzManager {

  TempProject project = TempProject.getInstance();

  String getClazzName();

  default List<? extends MasterClazz> readClazz(File f) {
    CtClass aClass = project.getCtClass(f);
    if (aClass == null)
      return new ArrayList<>();
    List<? extends MasterClazz> masterClazzes = readClazz(aClass);
    if (masterClazzes == null)
      return new ArrayList<>();
    for (MasterClazz readClazz : masterClazzes) {
      readClazz.setFile(f);
    }
    return masterClazzes;
  }

  default void writeClazz(MasterClazz clazz) {
    throw new NotImplementedException();
  }

  List<? extends MasterClazz> readClazz(CtClass aClass);

  default boolean detect(File f) {
    CtClass aClass = project.getCtClass(f);
    if (aClass != null)
      return detect(aClass);
    List<? extends MasterClazz> masterClazzes = readClazz(f);
    return masterClazzes != null && masterClazzes.size() > 0;
  }

  default boolean detect(CtClass aClass) {
    List<? extends MasterClazz> masterClazzes = readClazz(aClass);
    return masterClazzes != null && masterClazzes.size() > 0;
  }

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
