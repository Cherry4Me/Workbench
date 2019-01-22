package de.cherry.workbench.clazz.rest;

import de.cherry.workbench.clazz.ClazzFinder;
import de.cherry.workbench.clazz.MasterClazz;
import org.springframework.web.bind.annotation.RestController;
import spoon.reflect.declaration.CtClass;

public class RestFinder implements ClazzFinder {
  @Override
  public boolean detect(CtClass aClass) {
    RestController annotation = aClass.getAnnotation(RestController.class);
    return annotation != null;
  }

  @Override
  public String getClazzName() {
    return RestClazz.class.getSimpleName();
  }

  @Override
  public MasterClazz createClazz(CtClass aClass) {
    RestClazz restClazz = new RestClazz();
    restClazz.name = aClass.getSimpleName();
    return restClazz;
  }
}
