package de.cherry.workbench.self.interpreter;

public class Uiable<T> {
  public String className;
  public T state;

  public Uiable(T object) {
    state = object;
    className = object.getClass().getName();
  }
}
