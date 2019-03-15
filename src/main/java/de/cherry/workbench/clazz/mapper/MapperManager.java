package de.cherry.workbench.clazz.mapper;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;

import java.util.List;

public class MapperManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Mapper";
  }

  @Override
  public List<MasterClazz> readClazz(CtClass aClass) {
    return null;
  }

  @Override
  public boolean detect(CtClass aClass) {
    String predicateClassName = "java.util.function.Function";
    return implementsOrLamda(aClass, predicateClassName);
  }
}
