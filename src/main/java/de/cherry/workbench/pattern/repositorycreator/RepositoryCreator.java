package de.cherry.workbench.pattern.repositorycreator;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class RepositoryCreator implements PatternManager {

  That that = That.getInstance();


  @PostMapping("/repositoryTemplate")
  @Override
  public TypeSaveObject findPattern(@RequestBody Clazz2Edit className) {
    File file = new File(That.getInstance().get().path + className.file);
    for (ClazzManager clazzManager : that.clazzManagers) {
      if (clazzManager.getClazzName().equals(className.clazz)) {
        List<? extends MasterClazz> clazz = clazzManager.readClazz(file);
        MasterClazz clazz1 = clazz.get(0);
        clazz1.setFile(file);
        return new TypeSaveObject(clazz1);
      }
    }
    return null;
  }


  @Override
  public String getUrl() {
    return "/pattern/repositoryCreator.html";
  }

  @Override
  public String getName() {
    return "Repository Creator";
  }


  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return clazz.equals("ModelClazz");
  }
}
