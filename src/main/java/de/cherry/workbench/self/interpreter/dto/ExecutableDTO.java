package de.cherry.workbench.self.interpreter.dto;

import javafx.util.Pair;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

public class ExecutableDTO {

  private String name;

  private String returnType;

  private List<Pair<String, ClassDTO>> params;

  public ExecutableDTO(String name, String returnType, List<Pair<String, ClassDTO>> params) {
    this.name = name;
    this.returnType = returnType;
    this.params = params;
  }

  public static List<ExecutableDTO> from(List<java.lang.reflect.Executable> executables) {
    ArrayList<ExecutableDTO> executableDTOWOS = new ArrayList<>();
    for (Executable executable : executables) {
      executableDTOWOS.add(
          new ExecutableDTO(
              executable.getName()
              , executable.getAnnotatedReturnType().getType().getTypeName()
              , ParameterDTO.from(executable.getParameters())));

    }
    return executableDTOWOS;
  }

  public String getName() {
    return name;
  }

  public String getReturnType() {
    return returnType;
  }

  public List<Pair<String, ClassDTO>> getParams() {
    return params;
  }
}
