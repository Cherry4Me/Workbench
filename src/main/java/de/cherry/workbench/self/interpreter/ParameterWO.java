package de.cherry.workbench.self.interpreter;

import de.cherry.workbench.self.explorer.ClassWO;
import javafx.util.Pair;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class ParameterWO {

  public static ArrayList<Pair<String, ClassWO>> from(Parameter[] parameters) {
    ArrayList<Pair<String, ClassWO>> params = new ArrayList<>();
    for (Parameter parameter : parameters) {
      Class<?> type = parameter.getType();
      type = type.isArray() ? type.getComponentType() : type;
      params.add(new Pair<>(parameter.getName(), new ClassWO(type.getName())));
    }
    return params;
  }
}
