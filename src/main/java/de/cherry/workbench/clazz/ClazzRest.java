package de.cherry.workbench.clazz;

import de.cherry.workbench.clazz.model.ModelFinder;
import de.cherry.workbench.clazz.rest.RestFinder;
import de.cherry.workbench.clazz.wo.WoFinder;
import de.cherry.workbench.interpreter.Uiable;
import de.cherry.workbench.start.TempProject;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spoon.reflect.declaration.CtClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
public class ClazzRest {

  TempProject project = TempProject.getInstance();

  List<ClazzFinder> theFinders = Arrays.asList(
      new RestFinder(),
      new ModelFinder(),
      new WoFinder()
  );

  @PostMapping("/getState")
  public Uiable getState(@RequestBody ClassAndClazz classAndClazz) {
    ClazzFinder finder = getClazzFinder(classAndClazz);
    CtClass aClass = project.as.findClass(classAndClazz.aClass);
    MasterClazz clazz = finder.createClazz(aClass);
    return new Uiable(clazz);
  }

  @NotNull
  private ClazzFinder getClazzFinder(@RequestBody ClassAndClazz classAndClazz) {
    for (ClazzFinder finder : theFinders) {
      if (finder.getClazzName().equals(classAndClazz.aClazz)) {
        return finder;
      }
    }
    throw new IllegalArgumentException();
  }

  @GetMapping("clazz")
  public HashMap<String, List<String>> getClazz() {
    List<CtClass> classes = project.as.allSpoonClasses.getClasses();
    HashMap<String, List<String>> clazz4class = new HashMap<>();
    for (CtClass aClass : classes) {
      clazz4class.put(aClass.getQualifiedName(), new ArrayList<>());
      for (ClazzFinder finder : theFinders) {
        if (finder.detect(aClass)) {
          List<String> clazzes = clazz4class.get(aClass.getQualifiedName());
          clazzes.add(finder.getClazzName());
          clazz4class.put(aClass.getQualifiedName(), clazzes);
        }
      }
    }
    return clazz4class;
  }
}
