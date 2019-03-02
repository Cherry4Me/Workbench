package de.cherry.workbench.clazz.rest;

public enum HttpMethods {
  GET("org.springframework.web.bind.annotation.GetMapping"),
  POST("org.springframework.web.bind.annotation.PostMapping"),
  PUT("org.springframework.web.bind.annotation.PutMapping"),
  DELETE("org.springframework.web.bind.annotation.DeleteMapping"),
  PATCH("org.springframework.web.bind.annotation.PatchMapping");

  public String className;

  HttpMethods(String className) {

    this.className = className;
  }

  public String getClassName() {
    return className;
  }
}
