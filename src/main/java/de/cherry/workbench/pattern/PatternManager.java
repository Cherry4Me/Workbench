package de.cherry.workbench.pattern;

import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;

public interface PatternManager {

  String getUrl();

  String getName();

  TypeSaveObject findPattern(Clazz2Edit className);

  void writePattern();

  boolean isPatternStartableForClazz(String clazz);
}
