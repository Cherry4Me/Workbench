package de.cherry.workbench.system.impl;

import de.cherry.workbench.system.SystemManager;

public class SystemDTO {
  public String name;
  public String url;

  public SystemDTO(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public static SystemDTO from(SystemManager systemManager) {
    return new SystemDTO(
        systemManager.getName()
        , systemManager.getURL()
    );
  }
}
