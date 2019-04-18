package de.cherry.workbench.clazz.rest;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RestClazz implements MasterClazz {

  private String file;

  public String httpMethode;
  public String uri;
  public String methodName;
  public String requestBody;
  public List<RequestParam> requestParams;
  public List<PathVariable> pathVariables;
  public String resultType;

  public RestClazz(String methodName
      , String httpMethode
      , String uri
      , String requestBody
      , List<RequestParam> requestParams
      , ArrayList<PathVariable> pathVariables
      , String resultType
      , File file) {
    this.methodName = methodName;
    this.httpMethode = httpMethode;
    this.uri = uri;
    this.requestBody = requestBody;
    this.requestParams = requestParams;
    this.pathVariables = pathVariables;
    this.resultType = resultType;
    this.setFile(file);
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
