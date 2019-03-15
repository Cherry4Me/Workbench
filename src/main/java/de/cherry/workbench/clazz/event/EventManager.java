package de.cherry.workbench.clazz.event;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;

import java.util.List;

public class EventManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return null;
  }

  @Override
  public List<? extends MasterClazz> readClazz(CtClass aClass) {
    return null;
  }
}
