package de.cherry.workbench.meta.domain;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MyDomain {

  public String basePath;
  public String baseGroup;

  public List<Project> projects;

  public Project current;

  private MyDomain() {
  }

  public void set(Project newProject) {
    current = newProject;
    boolean isNew = true;
    for (Project project : projects) {
      if (newProject.name.equals(project.name)) {
        isNew = false;
      }
    }
    if (isNew)
      projects.add(newProject);
    write();
  }

  private void write() {
    Yaml yaml = new Yaml();
    String dump = yaml.dump(this);
    try {
      Files.write(Paths.get(getFile().getAbsolutePath()), dump.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public static MyDomain getInstance() {
    try {
      File initialFile = getFile();
      InputStream inputStream = new FileInputStream(initialFile);
      Yaml yaml = new Yaml();
      return yaml.load(inputStream);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private static File getFile() {
    return new File("domain.yaml");
  }
}