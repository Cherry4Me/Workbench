package de.cherry.workbench.general.spoon.restify;/*
package de.cherry.spoon.restify;

import spoon.processing.AbstractAnnotationProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.ModifierKind;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javaDir.lang.annotation.Annotation;
import javaDir.util.HashMap;

public class RestifyProcessor extends AbstractAnnotationProcessor<Restify, CtClass> {

  @Override
  public void process(Restify getter, CtClass ctClass) {
    System.out.println("add Rest");
    CtClass restClass = getFactory().createClass(ctClass.getPackage(), ctClass.getSimpleName() + "Rest");
    CtAnnotation<Annotation> path =
        getFactory().createAnnotation(getFactory().<Annotation>createCtTypeReference(Path.class));
    HashMap<String, CtExpression> values = new HashMap<>();
    values.put("value", getFactory().createCodeSnippetExpression("\"" + ctClass.getSimpleName() + "\""));
    path.setValues(values);
    restClass.addAnnotation(path);
    restClass.setDocComment("This is a generated REST for " + ctClass.getQualifiedName());
    RestContainer.getInstance(getFactory()).registerRest(restClass);

    CtMethod<Object> all = getRestMethod(GET.class, ctClass);
    all.setSimpleName("all");
    //remove path Annotation
    all.removeAnnotation(all.getAnnotations().get(1));
    all.getBody().insertBegin(getFactory().createCodeSnippetStatement("return null"));
    restClass.addMethod(all);

    CtMethod<Object> get = getRestMethod(GET.class, ctClass);
    get.getBody().insertBegin(getFactory().createCodeSnippetStatement("return null"));
    restClass.addMethod(get);

    CtMethod<Object> put = getRestMethod(PUT.class, ctClass);
    put.getBody().insertBegin(getFactory().createCodeSnippetStatement("return null"));
    restClass.addMethod(put);

    CtMethod<Object> post = getRestMethod(POST.class, ctClass);
    post.getBody().insertBegin(getFactory().createCodeSnippetStatement("return null"));
    restClass.addMethod(post);


    CtMethod<Object> del = getRestMethod(DELETE.class, ctClass);
    del.getBody().insertBegin(getFactory().createCodeSnippetStatement("return null"));
    restClass.addMethod(del);

  }


  private CtMethod<Object> getRestMethod(Class annotation, CtClass entityClass) {
    CtMethod<Object> restMethod = getFactory().createMethod();
    restMethod.addAnnotation(getFactory().createAnnotation(getFactory().<Annotation>createCtTypeReference(annotation)));
    CtAnnotation<Annotation> path =
        getFactory().createAnnotation(getFactory().<Annotation>createCtTypeReference(Path.class));
    HashMap<String, CtExpression> values = new HashMap<>();
    values.put("value", getFactory().createCodeSnippetExpression("\"/{" + entityClass.getSimpleName() + "}\""));
    path.setValues(values);
    restMethod.addAnnotation(path);
    restMethod.setSimpleName(annotation.getSimpleName().toLowerCase());
    CtBlock<Object> body = getFactory().createBlock();
    restMethod.setBody(body);
    restMethod.setType(getFactory().createCtTypeReference(Response.class));
    restMethod.addModifier(ModifierKind.PUBLIC);
    return restMethod;
  }
}
*/