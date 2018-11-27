package de.cherry.workbench.spoon;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;

import java.util.ArrayList;
import java.util.List;

public class AllClassProcessor extends AbstractProcessor<CtClass> {


  private List<CtClass> classes = new ArrayList<>();

  public List<CtClass> getClasses() {
    return classes;
  }

  public void process(CtClass ctClass) {
    System.out.println(ctClass.getSimpleName());
    classes.add(ctClass);

  }
}
