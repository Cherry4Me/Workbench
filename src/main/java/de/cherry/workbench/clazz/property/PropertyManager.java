package de.cherry.workbench.clazz.property;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.meta.java.JTool;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PropertyManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Property";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = CurrentProject.getInstance().j;
    CtClass aClass = j.getCtClass(f);
    if (aClass == null)
      return null;
    ProbertyClazz probertyClazz = new ProbertyClazz();
    for (Object o : aClass.getFields()) {
      CtField field = (CtField) o;
      String fieldName = field.getSimpleName();
      CtMethod getter = aClass.getMethod(getterName(fieldName));
      CtMethod setter = aClass.getMethod(setterName(fieldName));
      probertyClazz.fields.put(fieldName
          , new GetterAndSetter(getter != null, setter != null));
    }
    if (probertyClazz.fields.isEmpty()) return null;
    return Arrays.asList(probertyClazz);
  }

  private String setterName(String fieldName) {
    return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }

  private String getterName(String fieldName) {
    return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }
}
