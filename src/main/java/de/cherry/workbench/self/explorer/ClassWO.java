package de.cherry.workbench.self.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassWO {
  public String name;

  public ClassWO() {
  }

  public ClassWO(String name) {
    this.name = name;
  }

  public static List<ClassWO> from(Set<Class<?>> classes){
    ArrayList<ClassWO> classWOS = new ArrayList<>();
    for (Class aClass : classes) {
      classWOS.add(new ClassWO(aClass.getName()));
    }
    return classWOS;
  }
}
