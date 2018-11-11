package de.cherry.workbench.interpreter;

import de.cherry.workbench.explorer.ClassWO;
import javafx.util.Pair;

import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class ParameterWO {

  public static ArrayList<Pair<String, ClassWO>> from(Parameter[] parameters) {
    ArrayList<Pair<String, ClassWO>> params = new ArrayList<>();
    for (Parameter parameter : parameters) {
      Class<?> type = parameter.getType();
      type = type.isArray() ? type.getComponentType() : type;
      String packageName = type.isPrimitive() ? null : type.getPackage().getName();
      params.add(new Pair<>(parameter.getName(), new ClassWO(type.getSimpleName(), packageName)));
    }
    return params;
  }
}
