package de.cherry.workbench.server;

import de.cherry.workbench.general.Dogsbody;
import de.cherry.workbench.general.Project;

import java.io.File;

public class ServerDogsbody implements Dogsbody {

  public static ApplicationServer create(File dir, Project project) throws Exception {

    if (!dir.isDirectory() | !dir.canWrite())
      throw new RuntimeException("nope");
    ApplicationServer server = new ApplicationServer();


    server.project = project;
    server.location = dir;
    server.init();

    return server;
  }

}
