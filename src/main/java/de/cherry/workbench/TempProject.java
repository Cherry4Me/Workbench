package de.cherry.workbench;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.ajax.AjaxManager;
import de.cherry.workbench.clazz.dynamic.DynamicManager;
import de.cherry.workbench.clazz.event.EventManager;
import de.cherry.workbench.clazz.filter.FilterManager;
import de.cherry.workbench.clazz.mapper.MapperManager;
import de.cherry.workbench.clazz.model.ModelManager;
import de.cherry.workbench.clazz.property.PropertyManager;
import de.cherry.workbench.clazz.rest.RestManager;
import de.cherry.workbench.clazz.stream.StreamManager;
import de.cherry.workbench.clazz.ui.UiManager;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.ClazzEditor;
import de.cherry.workbench.pattern.comperator.ComperatorEditor;
import de.cherry.workbench.pattern.desinger.DesignerManager;
import de.cherry.workbench.pattern.dynamiceditor.DynamicEditor;
import de.cherry.workbench.pattern.filter.FilterEditor;
import de.cherry.workbench.pattern.stream.StreamEditor;
import de.cherry.workbench.self.server.ApplicationServer;
import de.cherry.workbench.self.server.Project;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.api.ApiManager;
import de.cherry.workbench.system.clazz2file.Clazz2FileDTO;
import de.cherry.workbench.system.clazz2file.Clazz2FileManager;
import de.cherry.workbench.system.erm.ErmManager;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class TempProject {

  private static TempProject ourInstance;

  public ApplicationServer as;

  public List<SystemManager> systemManagers;
  public List<PatternManager> patternManagers;
  public List<ClazzManager> clazzManagers;

  private TempProject() throws Exception {
    File out = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out");
    this.as = ApplicationServer.create(out, new Project("com.example.out", "out"));
  }

  public static TempProject getInstance() {
    if (ourInstance == null) {
      try {
        ourInstance = new TempProject();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      ourInstance.clazzManagers = Arrays.asList(
          new RestManager(),
          new ModelManager(),
          new UiManager(),
          new PropertyManager(),
          new StreamManager(),
          new FilterManager(),
          new MapperManager(),
          new AjaxManager(),
          new EventManager(),
          new DynamicManager()
      );
      ourInstance.patternManagers = Arrays.asList(
          new ClazzEditor(),
          new DesignerManager(),
          new StreamEditor(),
          new FilterEditor(),
          new ComperatorEditor(),
          new DynamicEditor()
      );
      ourInstance.systemManagers = Arrays.asList(
          new Clazz2FileManager(),
          new ErmManager(),
          new ApiManager()
      );
    }
    return ourInstance;
  }

  public static void walk(String path, Clazz2FileDTO clazz2FileDTO, BiConsumer<Clazz2FileDTO, File> forEach) {
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
                , inner
                , forEach);
          }
        } else {
          forEach.accept(inner, f);
        }
        clazz2FileDTO.inner.add(inner);
      }
  }

  public ClazzManager findClazzManager(Predicate<ClazzManager> isIt) {
    for (ClazzManager clazzManager : clazzManagers)
      if (isIt.test(clazzManager))
        return clazzManager;
    return null;
  }

  public PatternManager findPatternManager(Predicate<PatternManager> isIt) {
    for (PatternManager patternManager : patternManagers)
      if (isIt.test(patternManager))
        return patternManager;
    return null;
  }

  public SystemManager findSystemManager(Predicate<SystemManager> isIt) {
    for (SystemManager systemManager : systemManagers)
      if (isIt.test(systemManager))
        return systemManager;
    return null;
  }


  public CtClass getCtClass(File f) {
    String name = f.getName();
    if (!name.endsWith(".java"))
      return null;
    String className = name.replace(".java", "");
    for (CtClass aClass : this.as.allSpoonClasses.getClasses()) {
      if (className.equals(aClass.getSimpleName()))
        return aClass;

    }
    return null;
  }
}
