package de.cherry.workbench.clazz.dynamic;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.util.List;

public class DynamicManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Dynamic";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    return null;
  }
}
