package de.cherry.workbench.clazz.mapper;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.java.JTool;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.util.List;

public class MapperManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Mapper";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = That.getInstance().getJ();
    CtClass aClass = j.getCtClass(f);
    if (aClass == null)
      return null;
    String predicateClassName = "java.util.function.Function";
    j.implementsOrLamda(aClass, predicateClassName);
    return null;
  }
}
