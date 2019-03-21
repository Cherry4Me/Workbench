package de.cherry.workbench.clazz;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.List;

public interface ClazzManager {

  String getClazzName();

  List<? extends MasterClazz> readClazz(File f);

  default void writeClazz(MasterClazz clazz) {
    throw new NotImplementedException();
  }

}
