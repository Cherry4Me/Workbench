package de.cherry.workbench.self.interpreter.dto;

import javafx.util.Pair;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class ParameterDTO {

  public static ArrayList<Pair<String, ClassDTO>> from(Parameter[] parameters) {
    ArrayList<Pair<String, ClassDTO>> params = new ArrayList<>();
    for (Parameter parameter : parameters) {
      Class<?> type = parameter.getType();
      type = type.isArray() ? type.getComponentType() : type;
      params.add(new Pair<>(parameter.getName(), new ClassDTO(type.getName())));
    }
    return params;
  }
}
