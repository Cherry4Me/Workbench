package de.cherry.workbench.clazz.rest;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import org.springframework.web.bind.annotation.RestController;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class RestManager implements ClazzManager {


  @Override
  public String getClazzName() {
    return RestClazz.class.getSimpleName();
  }

  @Override
  public MasterClazz createClazz(CtClass aClass) {
    RestClazz restClazz = new RestClazz();
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
      restClazz.services.add(new Service(methodName, httpMethode, uri, requestBody, requestParams, pathVariables));
    }
    return restClazz;
  }

  @Override
  public boolean detect(CtClass aClass) {
    RestController annotation = aClass.getAnnotation(RestController.class);
    return annotation != null;
  }
}
