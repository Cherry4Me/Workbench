package de.cherry.workbench.domain.terminal;

public class TextBuilder implements Builder {
  StringBuilder stringBuilder = new StringBuilder();

  @Override
  public void add(String line) {
    stringBuilder.append(line).append(System.lineSeparator());
  }

  @Override
  public String getString() {
    return stringBuilder.toString();
  }
}
