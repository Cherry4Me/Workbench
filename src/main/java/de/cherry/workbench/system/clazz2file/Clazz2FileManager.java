package de.cherry.workbench.system.clazz2file;

import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.clazz.ClassAndClazz;
import de.cherry.workbench.meta.file.FileTool;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.system.SystemManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class Clazz2FileManager implements SystemManager {

  CurrentProject project = CurrentProject.getInstance();


  @Override
  public String getURL() {
    return "clazz2file.html";
  }

  @Override
  public String getName() {
    return "Clazzes 2 File";
  }


  @GetMapping("clazzes4files")
  public Clazz2FileDTO getModel() {
    String base = That.getInstance().domain.current.path;
    Clazz2FileDTO clazz2FileDTO = new Clazz2FileDTO(base);
    FileTool.walk(
        base,
        clazz2FileDTO,
        (inner, f) -> inner.clazzes.addAll(getClazzes(f, project))
    );

    return clazz2FileDTO;
  }

  @GetMapping("clazz")
  public HashMap<String, List<String>> getClazz() {
    List<CtClass> classes = project.j.allSpoonClasses.getClasses();
    HashMap<String, List<String>> clazz4class = new HashMap<>();
    for (CtClass aClass : classes) {
      clazz4class.put(aClass.getQualifiedName(), new ArrayList<>());
      for (ClazzManager finder : project.clazzManagers) {
        List<String> clazzes = clazz4class.get(aClass.getQualifiedName());
        clazzes.add(finder.getClazzName());
        clazz4class.put(aClass.getQualifiedName(), clazzes);
      }
    }
    return clazz4class;
  }


  @GetMapping("getclazzes")
  public List<ClazzManager> getClazzes() {
    return project.clazzManagers;
  }

  @PostMapping("/getState")
  public TypeSaveObject getState(@RequestBody ClassAndClazz classAndClazz) {
    ClazzManager finder = getClazzFinder(classAndClazz);
    File aClassFile = project.j.findFile(classAndClazz.aClass);
    MasterClazz clazz = finder.readClazz(aClassFile).get(0);
    return new TypeSaveObject(clazz);
  }


  @NotNull
  private ClazzManager getClazzFinder(@RequestBody ClassAndClazz classAndClazz) {
    for (ClazzManager finder : project.clazzManagers) {
      if (finder.getClazzName().equals(classAndClazz.aClazz)) {
        return finder;
      }
    }
    throw new IllegalArgumentException();
  }

  private List<String> getClazzes(File f, CurrentProject project) {
    ArrayList<String> clazzes = new ArrayList<>();
    for (ClazzManager finder : project.clazzManagers) {
      List<? extends MasterClazz> masterClazzes = finder.readClazz(f);
      if (masterClazzes != null)
        for (int i = 0; i < masterClazzes.size(); i++) {
          clazzes.add(finder.getClazzName());
        }
    }
    return clazzes;
  }
}
