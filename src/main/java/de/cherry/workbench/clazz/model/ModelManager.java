package de.cherry.workbench.clazz.model;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

public class ModelManager implements ClazzManager {

  @Override
  public String getClazzName() {
    return ModelClazz.class.getSimpleName();
  }

  @Override
  public MasterClazz readClazz(CtClass aClass) {
    ModelClazz modelClazz = new ModelClazz();
    for (Object field : aClass.getFields()) {
      CtField ctField = (CtField) field;
      if (!ctField.isStatic())
        modelClazz.fields.put(ctField.getSimpleName(), ctField.getType().getQualifiedName());
    }
    return modelClazz;
  }

  @Override
  public boolean detect(CtClass aClass) {
    if (aClass == null) return false;
    for (String part : aClass.getQualifiedName().split("\\.")) {
      if (part.equals("domain"))
        return true;
    }
    return false;
  }
}
