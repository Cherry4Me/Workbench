package de.cherry.workbench.spoon;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.template.AbstractTemplate;

public class TemplateRemoverProcessor extends AbstractProcessor<CtClass> {

  @Override
  public void process(CtClass ctClass) {
    try {
      Class actualClass = ctClass.getActualClass();
      if (actualClass == null) {
        return;
      }
      if (AbstractTemplate.class.isAssignableFrom(actualClass)) {
        System.out.println(ctClass.getSimpleName());
      }


    } catch (Exception e) {

    }

  }
}
