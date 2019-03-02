package de.cherry.workbench.self.interpreter.dto;

import javafx.util.Pair;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MethodeDTO {

  String className;

  String mehtodeName;

  List<Pair<String, ClassDTO>> params;


  public MethodeDTO(String className, String mehtodeName, List<Pair<String, ClassDTO>> params) {
    this.className = className;
    this.mehtodeName = mehtodeName;
    this.params = params;
  }

  public static List<MethodeDTO> from(List<Method> methods) {
    ArrayList<MethodeDTO> methodeDTOS = new ArrayList<>();
    for (Method method : methods) {
      Parameter[] parameters = method.getParameters();
      ArrayList<Pair<String, ClassDTO>> params = ParameterDTO.from(parameters);
      methodeDTOS.add(new MethodeDTO(method.getDeclaringClass().getName(), method.getName(), params));
    }
    return methodeDTOS;
  }

  public String getClassName() {
    return className;
  }

  public String getMehtodeName() {
    return mehtodeName;
  }

  public List<Pair<String, ClassDTO>> getParams() {
    return params;
  }


}
