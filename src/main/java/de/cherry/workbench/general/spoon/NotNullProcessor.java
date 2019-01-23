package de.cherry.workbench.general.spoon;

import com.sun.istack.internal.NotNull;
import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.code.CtAssert;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;

class NotNullProcessor extends AbstractAnnotationProcessor<NotNull, CtParameter> {
  @Override
  public void process(NotNull anno, CtParameter param) {
    CtMethod<?> method = param.getParent(CtMethod.class);
    CtBlock<?> body = method.getBody();
    CtAssert<Object> anAssert = getFactory().createAssert();
    CtCodeSnippetExpression<Object> paramExpression = getFactory().createCodeSnippetExpression
        (String.format("%s != null ", param.getSimpleName()));
    anAssert.setExpression(paramExpression);
    CtCodeSnippetExpression<Boolean> aTrue = getFactory().createCodeSnippetExpression("true");
    anAssert.setAssertExpression(aTrue);
//    CtAssert<?> assertion = constructAssertion(); param.getSimpleName()
    body.insertBegin(anAssert);
  }
}
