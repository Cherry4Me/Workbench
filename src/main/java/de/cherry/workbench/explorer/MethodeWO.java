package de.cherry.workbench.explorer;

import de.cherry.workbench.interpreter.ParameterWO;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MethodeWO {

  String className;

  String mehtodeName;

  List<Pair<String, ClassWO>> params;


  public MethodeWO(String className, String mehtodeName, List<Pair<String, ClassWO>> params) {
    this.className = className;
    this.mehtodeName = mehtodeName;
    this.params = params;
  }

  public String getClassName() {
    return className;
  }

  public String getMehtodeName() {
    return mehtodeName;
  }

  public List<Pair<String, ClassWO>> getParams() {
    return params;
  }

  public static List<MethodeWO> from(List<Method> methods) {
    ArrayList<MethodeWO> methodeWOS = new ArrayList<>();
    for (Method method : methods) {
      Parameter[] parameters = method.getParameters();
      ArrayList<Pair<String, ClassWO>> params = ParameterWO.from(parameters);
      methodeWOS.add(new MethodeWO(method.getDeclaringClass().getName(), method.getName(), params));
    }
    return methodeWOS;
  }


}
