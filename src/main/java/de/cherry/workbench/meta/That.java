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
import de.cherry.workbench.domain.DomainManager;
import de.cherry.workbench.domain.docker.DockerManager;
import de.cherry.workbench.domain.projects.Projects;
import de.cherry.workbench.domain.terminal.Terminal;
import de.cherry.workbench.meta.domain.MyDomain;
import de.cherry.workbench.meta.domain.Project;
import de.cherry.workbench.meta.java.JTool;
import de.cherry.workbench.meta.js.JsTool;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.ClazzEditor;
import de.cherry.workbench.pattern.comperator.ComperatorEditor;
import de.cherry.workbench.pattern.desinger.DesignerManager;
import de.cherry.workbench.pattern.dynamiceditor.DynamicEditor;
import de.cherry.workbench.pattern.filter.FilterEditor;
import de.cherry.workbench.pattern.repositorycreator.RepositoryCreator;
import de.cherry.workbench.pattern.stream.StreamEditor;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.api.ApiManager;
import de.cherry.workbench.system.clazz2file.Clazz2FileManager;
import de.cherry.workbench.system.erm.ErmManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class That {
  private static That ourInstance = null;

  public List<DomainManager> domainManagers;
  public List<SystemManager> systemManagers;
  public List<PatternManager> patternManagers;
  public List<ClazzManager> clazzManagers;
  private JTool j = null;
  private JsTool js = null;

  public static That getInstance() {
    if (ourInstance == null) {
      try {
        ourInstance = new That();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      ourInstance.clazzManagers = Arrays.asList(
          //new RestManager(),
          new ModelManager()
          //new UiManager(),
          //new PropertyManager(),
          //new StreamManager(),
          //new FilterManager(),
          //new MapperManager(),
          //new AjaxManager(),
          //new EventManager(),
          //new DynamicManager()
      );
      ourInstance.patternManagers = Arrays.asList(
          new ClazzEditor(),
          new RepositoryCreator()
          //new DesignerManager(),
          //new StreamEditor(),
          //new FilterEditor(),
          //new ComperatorEditor(),
          //new DynamicEditor()
      );
      ourInstance.systemManagers = Arrays.asList(
          new Clazz2FileManager(),
          new ErmManager()
          //new ApiManager()
      );

      ourInstance.domainManagers = Arrays.asList(
          new Projects()
          //new DockerManager(),
          //new Terminal()
      );
    }
    return ourInstance;
  }

  private That() {
  }

  private MyDomain myDomain = MyDomain.getInstance();


  public JTool getJ() {
    if (j == null)
      try {
        j = new JTool(new File(String.join(File.separator,
            myDomain.current.path, "src", "main", "java")));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        j = null;
      }
    return j;
  }

  public JsTool getJs() {
    if (js == null)
      try {
        js = new JsTool(new File(String.join(File.separator,
            myDomain.current.path, "src", "main", "webapp")));
      } catch (Exception e) {
        e.printStackTrace();
        js = null;
      }
    return js;
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

  public void set(Project current) {
    j = null;
    js = null;
    myDomain.set(current);
  }

  public Project get(){
    return myDomain.current;
  }
}
