package de.cherry.workbench.clazz.model;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.java.JTool;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class ModelManager implements ClazzManager {

  @Override
  public String getClazzName() {
    return ModelClazz.class.getSimpleName();
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = That.getInstance().getJ();
    CtClass aClass = j.getCtClass(f);
    if (aClass == null)
      return null;
    if (!detect(aClass)) return null;
    ModelClazz modelClazz = new ModelClazz();
    for (Object field : aClass.getFields()) {
      CtField ctField = (CtField) field;
      if (!ctField.isStatic())
        modelClazz.fields.put(ctField.getSimpleName(), ctField.getType().getQualifiedName());
    }
    return Arrays.asList(modelClazz);
  }

  public boolean detect(CtClass aClass) {
    if (aClass == null) return false;
    boolean isEntity = false;
    for (CtAnnotation<? extends Annotation> annotation : aClass.getAnnotations()) {
      if (annotation.getAnnotationType().getSimpleName().equals("Entity")) {
        isEntity = true;
      }
    }
    return isEntity;
  }
}
