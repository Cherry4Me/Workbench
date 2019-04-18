package de.cherry.workbench.domain.projects;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ProjectsTest {

  @Test
  public void createProject() throws IOException {
    ToCreate toCreate = new ToCreate();
    toCreate.path = "/Users/mbaaxur/Documents/gits/WorkbenchCherry/abc";
    toCreate.group = "de.example";
    toCreate.name = "abc";
    toCreate.template = "Java-Maven-Spring-Boot-Web-Server";
    File file = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/abc");
   //  FileUtils.deleteDirectory(file);
    //assertTrue(file.mkdir());
   // boolean project = new Projects().createProject(toCreate);
    // System.out.println(project);

  }
}