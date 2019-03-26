package de.cherry.workbench.clazz.rest;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.java.JTool;
import org.springframework.web.bind.annotation.RestController;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class RestManager implements ClazzManager {


  @Override
  public String getClazzName() {
    return "Rest";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = That.getInstance().getJ();
    CtClass aClass = j.getCtClass(f);
    if (aClass == null)
      return null;
    List<RestClazz> restClazzes = new ArrayList<>();
    if (aClass == null) return restClazzes;
    if (aClass.getAnnotation(RestController.class) == null) return restClazzes;
    for (Object o : aClass.getMethods()) {
      CtMethod method = (CtMethod) o;
      String methodName = method.getSimpleName();
      HttpMethods httpMethode = null;
      String uri = null;
      for (CtAnnotation<? extends Annotation> annotation : method.getAnnotations()) {
        for (HttpMethods httpMethod : HttpMethods.values()) {
          if (httpMethod.className.equals(annotation.getAnnotationType().toString())) {
            httpMethode = httpMethod;
            uri = annotation.getValue("name").toString();
          }
        }
      }
      String requestBody = null;
      List<RequestParam> requestParams = new ArrayList<>();
      ArrayList<PathVariable> pathVariables = new ArrayList<>();
      for (Object param : method.getParameters()) {
        CtParameter parameter = (CtParameter) param;
        for (CtAnnotation<? extends Annotation> annotation : parameter.getAnnotations()) {
          String annoType = annotation.getAnnotationType().toString();
          String paramType = parameter.getType().getQualifiedName();
          String paramName = parameter.getSimpleName();
          if ("org.springframework.web.bind.annotation.RequestBody".equals(annoType)) {
            requestBody = paramType;
          }
          if ("org.springframework.web.bind.annotation.RequestParam".equals(annoType)) {
            CtExpression name = annotation.getValue("value");
            requestParams.add(new RequestParam(paramName, paramType, name.toString()));
          }
          if ("org.springframework.web.bind.annotation.PathVariable".equals(annoType)) {
            pathVariables.add(new PathVariable(paramName, paramType));
          }
        }
      }
      restClazzes.add(new RestClazz(methodName, httpMethode, uri, requestBody, requestParams, pathVariables));
    }
    return restClazzes;
  }
}
