package de.cherry.workbench.start;

import de.cherry.workbench.general.Project;
import de.cherry.workbench.server.ApplicationServer;
import de.cherry.workbench.server.ServerDogsbody;

import java.io.File;

public class TempProject {

  private static TempProject ourInstance;

  public final ApplicationServer as;

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
