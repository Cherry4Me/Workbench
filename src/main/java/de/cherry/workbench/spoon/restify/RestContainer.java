package de.cherry.workbench.spoon.restify;/*
package de.cherry.spoon.restify;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javaDir.lang.annotation.Annotation;
import javaDir.util.HashMap;
import javaDir.util.HashSet;
import javaDir.util.Set;


public class RestContainer {

  private static RestContainer ourInstance = new RestContainer();
  private Factory factory;
  private CtClass restContainer;
  private CtMethod<Object> getClasses;
  private Set<CtClass> classes = new HashSet<>();

  public static RestContainer getInstance(Factory factory) {
    ourInstance.setFactory(factory);
    return ourInstance;
  }

  private RestContainer() {
  }

  public void registerRest(CtClass rest) {
    if (this.restContainer == null) {
      restContainer = factory.createClass(rest.getPackage(), "RestContainer");
      CtAnnotation<Annotation> annotation =
          getFactory().createAnnotation(getFactory().<Annotation>createCtTypeReference(ApplicationPath.class));
      HashMap<String, CtExpression> values = new HashMap<>();
      values.put("value", getFactory().createCodeSnippetExpression("\"rest\""));
      annotation.setValues(values);
      restContainer.addAnnotation(annotation);
      CtTypeReference<Object> applicationReference = getFactory().createCtTypeReference(Application.class);
      restContainer.setSuperclass(applicationReference);
      getClasses = getFactory().createMethod();
      getClasses.setSimpleName("getClasses");
      CtTypeReference<Object> setTypeSet = getFactory().createCtTypeReference(Set.class);
      getClasses.setType(setTypeSet);
      getClasses.addModifier(ModifierKind.PUBLIC);
      restContainer.addMethod(getClasses);
    }
    classes.add(rest);
    CtBlock<Object> codeBlock = getFactory().createBlock();
    CtLocalVariable<Object> localVariable = getFactory().createLocalVariable();
    localVariable.setSimpleName("classes");
    localVariable.setType(getFactory().createCtTypeReference(HashSet.class));
    localVariable.setAssignment(getFactory().createCodeSnippetExpression("new HashSet<>()"));
    codeBlock.insertBegin(localVariable);
    for (CtClass aClass : classes) {
      CtCodeSnippetStatement registerClass =
          getFactory().createCodeSnippetStatement(String.format("classes.add(%s.class)", aClass.getSimpleName()));
      codeBlock.insertEnd(registerClass);
    }
    codeBlock.insertEnd(getFactory().createCodeSnippetStatement("return classes"));
    getClasses.setBody(codeBlock);
  }

  public void setFactory(Factory factory) {
    this.factory = factory;
  }

  public Factory getFactory() {
    return factory;
  }
}
*/
