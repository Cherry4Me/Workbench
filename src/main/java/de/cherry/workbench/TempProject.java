package de.cherry.workbench;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.filter.FilterManager;
import de.cherry.workbench.clazz.mapper.MapperManager;
import de.cherry.workbench.clazz.model.ModelManager;
import de.cherry.workbench.clazz.property.PropertyManager;
import de.cherry.workbench.clazz.rest.RestManager;
import de.cherry.workbench.clazz.stream.StreamManager;
import de.cherry.workbench.clazz.ui.UiManager;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.comperator.ComperatorEditor;
import de.cherry.workbench.pattern.filter.FilterEditor;
import de.cherry.workbench.self.server.ApplicationServer;
import de.cherry.workbench.self.server.Project;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.clazz2file.Clazz2FileManager;
import de.cherry.workbench.pattern.clazzeditor.ClazzEditor;
import de.cherry.workbench.pattern.desinger.DesignerManager;
import de.cherry.workbench.system.erm.ErmManager;
import de.cherry.workbench.pattern.stream.StreamEditor;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TempProject {

  private static TempProject ourInstance;

  public final ApplicationServer as;

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
          new MapperManager()
      );
      ourInstance.patternManagers = Arrays.asList(
          new ClazzEditor(),
          new DesignerManager(),
          new StreamEditor(),
          new FilterEditor(),
          new ComperatorEditor()
      );
      ourInstance.systemManagers = Arrays.asList(
          new Clazz2FileManager(),
          new ErmManager()
      );
    }
    return ourInstance;
  }

  public CtClass getCtClass(File f) throws ClassNotFoundException {
    String name = f.getName();
    if (!name.endsWith(".java"))
      throw new ClassNotFoundException();
    String className = name.replace(".java", "");
    for (CtClass aClass : this.as.allSpoonClasses.getClasses()) {
      if (className.equals(aClass.getSimpleName()))
        return aClass;

    }
    return null;
  }
}
