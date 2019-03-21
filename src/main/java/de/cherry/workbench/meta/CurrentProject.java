package de.cherry.workbench.meta;

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
import de.cherry.workbench.meta.java.JTool;
import de.cherry.workbench.meta.js.JsTool;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.ClazzEditor;
import de.cherry.workbench.pattern.comperator.ComperatorEditor;
import de.cherry.workbench.pattern.desinger.DesignerManager;
import de.cherry.workbench.pattern.dynamiceditor.DynamicEditor;
import de.cherry.workbench.pattern.filter.FilterEditor;
import de.cherry.workbench.pattern.stream.StreamEditor;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.api.ApiManager;
import de.cherry.workbench.system.clazz2file.Clazz2FileManager;
import de.cherry.workbench.system.erm.ErmManager;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class CurrentProject {

  private static CurrentProject ourInstance;

  public That that = That.getInstance();

  public JTool j;
  public JsTool js;

  public List<SystemManager> systemManagers;
  public List<PatternManager> patternManagers;
  public List<ClazzManager> clazzManagers;


  private CurrentProject() throws Exception {
  }

  public static CurrentProject getInstance() {
    if (ourInstance == null) {
      try {
        ourInstance = new CurrentProject();
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
}
