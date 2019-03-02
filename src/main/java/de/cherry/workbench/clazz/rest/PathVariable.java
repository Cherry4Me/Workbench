package de.cherry.workbench.clazz.rest;

public class PathVariable {
  public final String paramName;
  public final String paramType;

  public PathVariable(String paramName, String paramType) {

    this.paramName = paramName;
    this.paramType = paramType;
  }
}
