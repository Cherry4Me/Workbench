package de.cherry.workbench.pattern.comperator;

import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;

public class ComperatorEditor implements PatternManager {
  @Override
  public String getUrl() {
    return "comperator.html";
  }

  @Override
  public String getName() {
    return "Comperator Editor";
  }

  @Override
  public TypeSaveObject findPattern(Clazz2Edit className) {
    return null;
  }

  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return true;
  }
}
