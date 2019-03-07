package de.cherry.workbench.system.clazzeditor;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.self.TempProject;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.system.SystemManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ClazzEditor implements SystemManager {

  TempProject project = TempProject.getInstance();


  @PostMapping("/clazzdata")
  public TypeSaveObject getClazzWithData(@RequestBody Clazz2Edit className) throws Exception {
    File file = new File(project.as.location.getAbsolutePath() + className.file);
    for (ClazzManager clazzManager : project.clazzManagers) {
      if (clazzManager.getClazzName().equals(className.clazz)) {
        MasterClazz clazz = clazzManager.createClazz(file);
        return new TypeSaveObject(clazz);
      }
    }
    return null;
  }


  @Override
  public String getURL() {
    return "./clazzEditor.html";
  }

  @Override
  public String getName() {
    return "Clazz Editor";
  }

  @Override
  public boolean isItTheRightOneFor(String clazz) {
    return true;
  }
}
