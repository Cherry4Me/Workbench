package de.cherry.workbench.clazz.rest;

public class RequestParam {
  public final String paramName;
  public final String paramType;
  public final String uri;

  public RequestParam(String paramName, String paramType, String uri) {
    this.paramName = paramName;
    this.paramType = paramType;
    this.uri = uri;
  }
}
