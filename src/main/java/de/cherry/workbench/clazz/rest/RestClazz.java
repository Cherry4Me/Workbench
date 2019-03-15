package de.cherry.workbench.clazz.rest;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RestClazz implements MasterClazz {

  private String file;

  public final HttpMethods httpMethode;
  public final String uri;
  public final String methodName;
  public final String requestBody;
  public final List<RequestParam> requestParams;
  public final ArrayList<PathVariable> pathVariables;

  public RestClazz(String methodName
      , HttpMethods httpMethode
      , String uri
      , String requestBody
      , List<RequestParam> requestParams
      , ArrayList<PathVariable> pathVariables) {
    this.methodName = methodName;
    this.httpMethode = httpMethode;
    this.uri = uri;
    this.requestBody = requestBody;
    this.requestParams = requestParams;
    this.pathVariables = pathVariables;
  }


  @Override
  public File getFile() {
    return new File(file);
  }

  @Override
  public void setFile(File f) {
    this.file = f.getAbsolutePath();
  }

}
