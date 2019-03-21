package de.cherry.workbench.meta.interpreter.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TypeSaveObject<T> {
  public Class type;
  public T state;

  public TypeSaveObject(Class type) {
    this.type = type;
    this.state = null;
  }

  public TypeSaveObject(T state) {
    if (state == null)
      throw new RuntimeException();
    this.state = state;
    this.type = state.getClass();
  }

  public TypeSaveObject(Class type, T state) {
    this.type = type;
    this.state = state;
  }

  public static TypeSaveObject form(String clazz, String object) throws ClassNotFoundException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Class<?> valueType = Class.forName(clazz);
    Object o = objectMapper.readValue(object, valueType);
    return new TypeSaveObject(valueType, o);
  }


  public boolean isNull() {
    return this.state == null;
  }
}
