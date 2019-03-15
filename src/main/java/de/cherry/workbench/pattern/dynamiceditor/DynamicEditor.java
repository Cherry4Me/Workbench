package de.cherry.workbench.pattern.dynamiceditor;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.ajax.AjaxManager;
import de.cherry.workbench.clazz.dynamic.DynamicManager;
import de.cherry.workbench.clazz.event.EventManager;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class DynamicEditor implements PatternManager {
  @Override
  public String getUrl() {
    return "dynamicEditor.html";
  }

  @Override
  public String getName() {
    return "Dynamic Editor";
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
    List<ClazzManager> clazzManagers = Arrays.asList(
        new AjaxManager(),
        new DynamicManager(),
        new EventManager()
    );
    for (ClazzManager clazzManager : clazzManagers) {
      if (clazz.equals(clazzManager.getClazzName()))
        return true;
    }
    return false;
  }
}
