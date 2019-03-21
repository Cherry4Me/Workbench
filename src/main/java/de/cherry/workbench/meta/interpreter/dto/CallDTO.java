package de.cherry.workbench.meta.interpreter.dto;

public class CallDTO {

  private String executable;
  private String clazz;
  private String object;
  private String[] paramsClasses;
  private String[] params;

  public CallDTO() {
  }

  public CallDTO(String executable, String clazz, String object, String[] paramsClasses, String[] params) {
    this.executable = executable;
    this.clazz = clazz;
    this.object = object;
    this.paramsClasses = paramsClasses;
    this.params = params;
  }

  public String getExecutable() {
    return executable;
  }

  public String getClazz() {
    return clazz;
  }

  public String getObject() {
    return object;
  }

  public String[] getParamsClasses() {
    return paramsClasses;
  }

  public String[] getParams() {
    return params;
  }
}
