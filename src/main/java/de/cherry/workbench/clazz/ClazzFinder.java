package de.cherry.workbench.clazz;

import spoon.reflect.declaration.CtClass;

public interface ClazzFinder {
  boolean detect(CtClass aClass);

  String getClazzName();

  MasterClazz createClazz(CtClass aClass);
}
