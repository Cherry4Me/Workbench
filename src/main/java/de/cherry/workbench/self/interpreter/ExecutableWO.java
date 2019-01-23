package de.cherry.workbench.self.interpreter;

import de.cherry.workbench.self.explorer.ClassWO;
import javafx.util.Pair;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

public class ExecutableWO {

  private String name;

  private String returnType;

  private List<Pair<String, ClassWO>> params;

  public ExecutableWO(String name, String returnType, List<Pair<String, ClassWO>> params) {
    this.name = name;
    this.returnType = returnType;
    this.params = params;
  }

  public String getName() {
    return name;
  }

  public String getReturnType() {
    return returnType;
  }

  public List<Pair<String, ClassWO>> getParams() {
    return params;
  }

  public static List<ExecutableWO> from(List<Executable> executables){
    ArrayList<ExecutableWO> executableWOS = new ArrayList<>();
    for (Executable executable : executables) {
      executableWOS.add(
          new ExecutableWO(
              executable.getName()
              , executable.getAnnotatedReturnType().getType().getTypeName()
              , ParameterWO.from(executable.getParameters())));

    }
    return executableWOS;
  }
}
