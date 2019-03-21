package de.cherry.workbench.pattern.stream;

import de.cherry.workbench.clazz.stream.StreamManager;
import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamEditor implements PatternManager {

  CurrentProject project = CurrentProject.getInstance();

  @PostMapping("/streamInfo")
  public TypeSaveObject getClazzWithData(@RequestBody Clazz2Edit className) throws Exception {
    return null;
  }


  @Override
  public String getUrl() {
    return "streamEditor.html";
  }

  @Override
  public String getName() {
    return "Stream Editor";
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
    return new StreamManager().getClazzName().equals(clazz);
  }
}
