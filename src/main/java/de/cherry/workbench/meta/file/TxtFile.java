package de.cherry.workbench.meta.file;

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
