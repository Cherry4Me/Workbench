package de.cherry.workbench.spoon.setter;

import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.TypeFactory;
import spoon.reflect.reference.CtTypeReference;

public class SetterProcessor extends AbstractAnnotationProcessor<Setter, CtClass> {
  @Override
  public void process(Setter getter, CtClass ctClass) {
    System.out.println("add Setter");

    for (int i = 0; i < ctClass.getFields().size(); i++) {
      CtField field = (CtField) ctClass.getFields().get(i);

      CtMethod<Object> method = getFactory().createMethod();
      CtParameter<Object> parameter = getFactory().createParameter();
      parameter.setType(field.getType());
      parameter.setSimpleName(field.getSimpleName());
      method.addParameter(parameter);
      CtBlock<Object> body = getFactory().createBlock();
      CtCodeSnippetStatement setStatement =
          getFactory().createCodeSnippetStatement(
              String.format("this.%s = %s", field.getSimpleName(), parameter.getSimpleName()));
      body.insertEnd(setStatement);
      method.setBody(body);
      method.setSimpleName(getSetterName(field.getSimpleName()));
      TypeFactory typeFactory = new TypeFactory();
      CtTypeReference aVoid = typeFactory.VOID_PRIMITIVE;
      method.setType(aVoid);
      method.addModifier(ModifierKind.PUBLIC);
      ctClass.addMethod(method);
    }
  }

  private String getSetterName(String fieldName) {
    return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }
}
