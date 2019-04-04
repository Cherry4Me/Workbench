package de.cherry.workbench.meta.java;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtInterface;

import java.util.ArrayList;
import java.util.List;

public class AllInterfaceProcessor extends AbstractProcessor<CtInterface> {


  private List<CtInterface> interfaces = new ArrayList<>();

  public List<CtInterface> getInterfaces() {
    return interfaces;
  }

  public void process(CtInterface ctInterface) {
    System.out.println(ctInterface.getSimpleName());
    interfaces.add(ctInterface);
  }
}
