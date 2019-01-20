package de.cherry.workbench.clazz;

public class ClazzAndState {
  public String className;
  public MasterClazz state;

  public ClazzAndState(MasterClazz clazz) {
    state = clazz;
    className = clazz.getClass().getName();

  }
}
