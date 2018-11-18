package de.cherry.workbench.server;

import de.cherry.workbench.general.Dogsbody;
import de.cherry.workbench.general.Project;

import java.io.File;

public class ServerDogsbody implements Dogsbody {

  public static ApplicationServer create(File dir, Project project) throws Exception {

    if (!dir.isDirectory() | !dir.canWrite())
      throw new RuntimeException("nope");
    ApplicationServer server = new ApplicationServer();


    server.setProject(project);
    server.setLocation(dir);
    server.build();


    return server;
  }

}
