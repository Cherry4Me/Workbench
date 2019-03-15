package de.cherry.workbench.clazz.filter;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;

import java.util.List;

public class FilterManager implements ClazzManager {


  @Override
  public String getClazzName() {
    return "Filter";
  }

  @Override
  public List<MasterClazz> readClazz(CtClass aClass) {
    return null;
  }

  @Override
  public boolean detect(CtClass aClass) {
    String predicateClassName = "java.util.function.Predicate";
    return implementsOrLamda(aClass, predicateClassName);
  }
}
