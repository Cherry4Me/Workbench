package de.cherry.workbench.system.erm;

public class Node {

  public String id;

  public float x;
  public float y;

  public String label;

  public String type;

  public String shape;

  public String color;

  public Node giveMeThat() {
    return this;
  }

  public String getId() {
    return id;
  }
}
