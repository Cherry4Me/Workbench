package de.cherry.workbench.clazz.wo;

import de.cherry.workbench.clazz.ClazzFinder;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

public class WoFinder implements ClazzFinder {
  @Override
  public boolean detect(CtClass aClass) {
    for (Object method : aClass.getMethods()) {
      CtMethod ctMethod = (CtMethod) method;
      String methodName = ctMethod.getSimpleName().toLowerCase();
      if (methodName.equals("towo") || methodName.equals("fromwo"))
        return true;
    }
    return false;
  }

  @Override
  public String getClazzName() {
    return WoClazz.class.getSimpleName();
  }

  @Override
  public MasterClazz createClazz(CtClass aClass) {
    WoClazz woClazz = new WoClazz();
    for (Object method : aClass.getMethods()) {
      CtMethod methodCt = (CtMethod) method;
      String methodName = methodCt.getSimpleName().toLowerCase();
      if (methodName.equals("towo") ){
        woClazz.toWo = true;
        CtParameter fistParam = (CtParameter) methodCt.getParameters().get(0);
        woClazz.forClass = fistParam.getType().getQualifiedName();
      }
      if(methodName.equals("fromwo")){
        woClazz.fromWo = true;
        woClazz.forClass = methodCt.getType().getQualifiedName();
      }
    }

    return woClazz;
  }
}
