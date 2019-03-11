package de.cherry.workbench.system.clazz2file;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.clazz.impl.ClassAndClazz;
import de.cherry.workbench.TempProject;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
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

  TempProject project = TempProject.getInstance();


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
    String base = project.as.location.getAbsolutePath();
    Clazz2FileDTO clazz2FileDTO = new Clazz2FileDTO(base);
    walk(
        base,
        clazz2FileDTO
    );

    return clazz2FileDTO;
  }

  @GetMapping("clazz")
  public HashMap<String, List<String>> getClazz() {
    List<CtClass> classes = project.as.allSpoonClasses.getClasses();
    HashMap<String, List<String>> clazz4class = new HashMap<>();
    for (CtClass aClass : classes) {
      clazz4class.put(aClass.getQualifiedName(), new ArrayList<>());
      for (ClazzManager finder : project.clazzManagers) {
        if (finder.detect(aClass)) {
          List<String> clazzes = clazz4class.get(aClass.getQualifiedName());
          clazzes.add(finder.getClazzName());
          clazz4class.put(aClass.getQualifiedName(), clazzes);
        }
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
    CtClass aClass = project.as.findClass(classAndClazz.aClass);
    MasterClazz clazz = finder.readClazz(aClass);
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


  public void walk(String path, Clazz2FileDTO clazz2FileDTO) {
    File root = new File(path);
    File[] list = root.listFiles();
    if (list == null)
      return;
    for (File f : list)
      if (!f.getName().startsWith(".")) {
        Clazz2FileDTO inner = new Clazz2FileDTO(f.getName());
        if (f.isDirectory()) {
          if (!f.getName().equals("target")) {
            walk(f.getAbsolutePath()
                , inner);
          }
        } else {
          inner.clazzes.addAll(getClazzes(f, project));
        }
        clazz2FileDTO.inner.add(inner);
      }
  }

  private List<String> getClazzes(File f, TempProject project) {
    ArrayList<String> clazzes = new ArrayList<>();
    for (ClazzManager finder : project.clazzManagers) {
      if (finder.detect(f)) {
        clazzes.add(finder.getClazzName());
      }
    }
    return clazzes;
  }
}
