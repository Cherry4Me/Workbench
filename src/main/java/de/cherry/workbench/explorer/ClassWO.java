package de.cherry.workbench.explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClassWO {
  String name;

  String packageName;

  public ClassWO() {
  }

  public ClassWO(String name, String packageName) {
    this.name = name;
    this.packageName = packageName;
  }

  public String getName() {
    return name;
  }

  public String getPackageName() {
    return packageName;
  }

  public static List<ClassWO> from(Set<Class<?>> classes){
    ArrayList<ClassWO> classWOS = new ArrayList<>();
    for (Class aClass : classes) {
      classWOS.add(new ClassWO(aClass.getSimpleName(), aClass.getPackage().getName()));
    }
    return classWOS;
  }
}
