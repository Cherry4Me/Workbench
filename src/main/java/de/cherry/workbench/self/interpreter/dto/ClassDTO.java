package de.cherry.workbench.self.interpreter.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassDTO {
  public String name;

  public ClassDTO() {
  }

  public ClassDTO(String name) {
    this.name = name;
  }

  public static List<ClassDTO> from(List<Class<?>> classes) {
    ArrayList<ClassDTO> classDTOS = new ArrayList<>();
    for (Class aClass : classes) {
      classDTOS.add(new ClassDTO(aClass.getName()));
    }
    return classDTOS;
  }

  public static List<ClassDTO> from(Set<Class<?>> classes) {
    ArrayList<ClassDTO> classDTOS = new ArrayList<>();
    for (Class aClass : classes) {
      classDTOS.add(new ClassDTO(aClass.getName()));
    }
    return classDTOS;
  }
}
