package de.cherry.workbench.domain.docker;

import de.cherry.workbench.domain.DomainManager;

public class DockerManager implements DomainManager {
  @Override
  public String getName() {
    return "Docker";
  }

  @Override
  public String getURL() {
    return "./docker.html";
  }
}
