package de.cherry.workbench.server;

import de.cherry.workbench.general.Project;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ServerDogsbodyTest {

  @Test
  public void create() throws Exception {
    File out = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out");
    ApplicationServer applicationServer = ServerDogsbody.create(out, new Project("de.test", "out"));
    applicationServer.build();


  }
}