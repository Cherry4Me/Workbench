package de.cherry.workbench.clazz;

import de.cherry.workbench.self.TempProject;
import spoon.reflect.declaration.CtClass;

import java.io.File;

public interface ClazzManager {

  TempProject project = TempProject.getInstance();

  String getClazzName();

  default MasterClazz createClazz(File f) {
    try {
      MasterClazz clazz = createClazz(project.getCtClass(f));
      clazz.setFile(f);
      return clazz;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }


  MasterClazz createClazz(CtClass aClass);

  default boolean detect(File f) {
    CtClass aClass = null;
    try {
      aClass = project.getCtClass(f);
    } catch (ClassNotFoundException e) {
      return false;
    }
    if (aClass == null) return false;
    return detect(aClass);
  }


  boolean detect(CtClass aClass);
}
