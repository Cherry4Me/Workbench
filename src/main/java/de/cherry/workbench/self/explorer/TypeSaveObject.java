package de.cherry.workbench.self.explorer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TypeSaveObject {
  private Class type;

  private Object state;

  public TypeSaveObject(Class type) {
    this.type = type;
    this.state = null;
  }

  public TypeSaveObject(Object state) {
    if (state == null)
      throw new RuntimeException();
    this.state = state;
    this.type = state.getClass();
  }

  public TypeSaveObject(Class type, Object state) {
    this.type = type;
    this.state = state;
  }

  public static TypeSaveObject form(String clazz, String object) throws ClassNotFoundException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Class<?> valueType = Class.forName(clazz);
    Object o = objectMapper.readValue(object, valueType);
    return new TypeSaveObject(valueType, o);
  }

  public Class getType() {
    return type;
  }

  public void setType(Class type) {
    this.type = type;
  }

  public Object getState() {
    return state;
  }

  public void setState(Object state) {
    this.state = state;
  }

  public boolean isNull() {
    return this.state == null;
  }
}
