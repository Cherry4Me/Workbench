package de.cherry.workbench.clazz.rest;

import java.util.ArrayList;
import java.util.List;

public class Service {
  public final String methodName;
  public final HttpMethods httpMethode;
  public final String uri;
  public final String requestBody;
  public final List<RequestParam> requestParams;
  public final ArrayList<PathVariable> pathVariables;

  public Service(String methodName, HttpMethods httpMethode, String uri, String requestBody, List<RequestParam> requestParams, ArrayList<PathVariable> pathVariables) {

    this.methodName = methodName;
    this.httpMethode = httpMethode;
    this.uri = uri;
    this.requestBody = requestBody;
    this.requestParams = requestParams;
    this.pathVariables = pathVariables;
  }
}
