package de.cherry.workbench.interpreter;

public class Dog4Interpreter {

  public String name;

  public boolean weiblich;

  public int alter;

  public static String run(String pace) {
    return "run-" + pace;
  }

  public Dog4Interpreter realClone() {
    Dog4Interpreter clone = new Dog4Interpreter();
    clone.name = name + "-clone";
    clone.alter = 0;
    clone.weiblich = weiblich;
    return clone;
  }

  public String barke() {
    return name + " sagt: Wuff!";
  }

  public int alterAfter(int years) {
    return alter + years;
  }
}
