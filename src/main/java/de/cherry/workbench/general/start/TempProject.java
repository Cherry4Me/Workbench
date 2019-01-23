package de.cherry.workbench.general.start;

import de.cherry.workbench.clazz.ClazzFinder;
import de.cherry.workbench.clazz.model.ModelFinder;
import de.cherry.workbench.clazz.rest.RestFinder;
import de.cherry.workbench.clazz.wo.WoFinder;
import de.cherry.workbench.general.Project;
import de.cherry.workbench.pattern.PatternFinder;
import de.cherry.workbench.pattern.crud.CrudFinder;
import de.cherry.workbench.pattern.erm.ErmFinder;
import de.cherry.workbench.general.server.ApplicationServer;
import de.cherry.workbench.general.server.ServerDogsbody;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TempProject {

  private static TempProject ourInstance;

  public final ApplicationServer as;

  public List<PatternFinder> patternFinders = Arrays.asList(
      new CrudFinder(),
      new ErmFinder()
  );

  public List<ClazzFinder> clazzFinders = Arrays.asList(
      new RestFinder(),
      new ModelFinder(),
      new WoFinder()
  );

  public static TempProject getInstance() {
    if (ourInstance == null) {
      try {
        ourInstance = new TempProject();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return ourInstance;
  }

  private TempProject() throws Exception {

    File out = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out");
    this.as = ServerDogsbody.create(out, new Project("com.example.out", "out"));
  }
}
