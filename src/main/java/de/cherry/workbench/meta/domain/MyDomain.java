package de.cherry.workbench.meta.domain;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MyDomain {

  public String firstName;
  public String lastName;
  public int age;

  public List<Project> projects;

  public Project current;

  private MyDomain(){
  }

  public static MyDomain getInstance(){
    try {
      Yaml yaml = new Yaml();
      File initialFile = new File("./domain.yaml");
      InputStream inputStream = new FileInputStream(initialFile);
      return yaml.load(inputStream);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}