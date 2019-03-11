package de.cherry.workbench.pattern.clazzeditor;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.TempProject;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.system.SystemManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ClazzEditor implements PatternManager {

  TempProject project = TempProject.getInstance();


  @PostMapping("/clazzdata")
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
  public String getUrl() {
    return "./clazzEditor.html";
  }

  @Override
  public String getName() {
    return "Clazz Editor";
  }


  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return true;
  }
}
