package de.cherry.workbench.pattern.clazzeditor;

import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class ClazzEditor implements PatternManager {

  CurrentProject project = CurrentProject.getInstance();


  @PostMapping("/clazzdata")
  @Override
  public TypeSaveObject findPattern(@RequestBody Clazz2Edit className) {
    File file = new File(That.getInstance().domain.current.path + className.file);
    for (ClazzManager clazzManager : project.clazzManagers) {
      if (clazzManager.getClazzName().equals(className.clazz)) {
        List<? extends MasterClazz> clazz = clazzManager.readClazz(file);
        return new TypeSaveObject(clazz.get(0));
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
