package de.cherry.workbench.clazz.filter;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;

public class FilterManager implements ClazzManager {


  @Override
  public String getClazzName() {
    return "Filter";
  }

  @Override
  public MasterClazz createClazz(CtClass aClass) {
    return null;
  }

  @Override
  public boolean detect(CtClass aClass) {
    String predicateClassName = "java.util.function.Predicate";
    return implementsOrLamda(aClass, predicateClassName);
  }
}
