package de.cherry.workbench.domain.terminal;

public interface Builder {
  void add(String line);
  String getString();

}
