package de.cherry.workbench.pattern.filter;

import de.cherry.workbench.TempProject;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class FilterEditor implements PatternManager {
  TempProject project = TempProject.getInstance();

  @Override
  public String getUrl() {
    return "/filter.html";
  }

  @Override
  public String getName() {
    return "FilterEditor";
  }

  @PostMapping("/getFilter")
  @Override
  public TypeSaveObject findPattern(@RequestBody Clazz2Edit className) {
    File file = new File(project.as.location.getAbsolutePath() + className.file);
    for (ClazzManager clazzManager : project.clazzManagers) {
      if (clazzManager.getClazzName().equals(className.clazz)) {
        MasterClazz clazz = clazzManager.readClazz(file);
        return new TypeSaveObject(clazz);
      }
    }
    return null;
  }

  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return clazz.equals(new de.cherry.workbench.clazz.filter.FilterManager().getClazzName());
  }
}
