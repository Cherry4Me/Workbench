package de.cherry.workbench.general;

public class TxtFile implements Fileable {
  @Override
  public String name() {
    return "test.txt";
  }

  @Override
  public String build() {
    return "hallo Welt";
  }
}
