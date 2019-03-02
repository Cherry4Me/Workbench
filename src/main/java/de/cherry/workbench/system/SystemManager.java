package de.cherry.workbench.system;

public interface SystemManager {
  String getURL();

  String getName();

  default boolean isItTheRightOneFor(String clazz) {
    return false;
  }

  ;
}
