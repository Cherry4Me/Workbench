package de.cherry.workbench.general;

public class Project {

  private String group;
  private String artifact;

  public String getGroup() {
    return group;
  }

  public String getArtifact() {
    return artifact;
  }

  public Project(String group, String artifact) {
    this.group = group;
    this.artifact = artifact;
  }

}
