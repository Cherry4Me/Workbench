package de.cherry.workbench.clazz.property;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;

public class PropertyManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Property";
  }

  @Override
  public MasterClazz readClazz(CtClass aClass) {
    ProbertyClazz probertyClazz = new ProbertyClazz();
    for (Object o : aClass.getFields()) {
      CtField field = (CtField) o;
      String fieldName = field.getSimpleName();
      CtMethod getter = aClass.getMethod(getterName(fieldName));
      CtMethod setter = aClass.getMethod(setterName(fieldName));
      probertyClazz.fields.put(fieldName
          , new GetterAndSetter(getter != null, setter != null));
    }
    return probertyClazz;
  }

  private String setterName(String fieldName) {
    return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }

  private String getterName(String fieldName) {
    return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }

  @Override
  public boolean detect(CtClass aClass) {
    return !aClass.getFields().isEmpty();
  }
}
