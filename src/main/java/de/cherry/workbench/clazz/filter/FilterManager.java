package de.cherry.workbench.clazz.filter;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.meta.java.JTool;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilterManager implements ClazzManager {


  @Override
  public String getClazzName() {
    return "Filter";
  }


  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = CurrentProject.getInstance().j;
    CtClass aClass = j.getCtClass(f);
    if (aClass == null)
      return null;
    String predicateClassName = "java.util.function.Predicate";

    j.implementsOrLamda(aClass, predicateClassName);
    return null;
  }

}
