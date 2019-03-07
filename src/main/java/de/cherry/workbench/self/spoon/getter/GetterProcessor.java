package de.cherry.workbench.self.spoon.getter;

import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

public class GetterProcessor extends AbstractAnnotationProcessor<Getter, CtClass> {
  @Override
  public void process(Getter getter, CtClass ctClass) {
    System.out.println("add Getter");

    for (int i = 0; i < ctClass.getFields().size(); i++) {
      CtField field = (CtField) ctClass.getFields().get(i);
      CtMethod<Object> method = getFactory().createMethod();
      CtReturn<Object> aReturn = getFactory().createReturn();
      CtCodeSnippetExpression<Object> fieldCall = getFactory().createCodeSnippetExpression
          (String.format("this.%s", field.getSimpleName()));
      aReturn.setReturnedExpression(fieldCall);
      method.setBody(aReturn);
      method.setSimpleName(getGetterName(field.getSimpleName()));
      method.setType(field.getType());
      method.addModifier(ModifierKind.PUBLIC);
      ctClass.addMethod(method);
    }
  }

  private String getGetterName(String fieldName) {
    return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
  }
}
