package de.cherry.workbench.clazz.model;

import de.cherry.workbench.clazz.ClazzFinder;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;

public class ModelFinder implements ClazzFinder {
  @Override
  public boolean detect(CtClass aClass) {
    for (String part : aClass.getQualifiedName().split("\\.")) {
      if (part.equals("domain"))
        return true;
    }
    return false;
  }

  @Override
  public String getClazzName() {
    return ModelClazz.class.getSimpleName();
  }

  @Override
  public MasterClazz createClazz(CtClass aClass) {
    ModelClazz modelClazz = new ModelClazz();
    modelClazz.name = aClass.getSimpleName();
    return modelClazz;
  }
}
