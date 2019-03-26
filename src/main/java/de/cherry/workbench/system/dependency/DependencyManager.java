package de.cherry.workbench.system.dependency;

import de.cherry.workbench.system.SystemManager;

public class DependencyManager implements SystemManager {
  @Override
  public String getURL() {
    return "/system/dependency.html";
  }

  @Override
  public String getName() {
    return "DependencyEditor";
  }
}
