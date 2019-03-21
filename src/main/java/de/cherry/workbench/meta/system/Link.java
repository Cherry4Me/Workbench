package de.cherry.workbench.meta.system;

import de.cherry.workbench.domain.DomainManager;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.system.SystemManager;

public class Link {
  public String name;
  public String url;

  public Link(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public static Link from(DomainManager domainManager) {
    return new Link(
        domainManager.getName()
        , domainManager.getURL()
    );
  }

  public static Link from(SystemManager systemManager) {
    return new Link(
        systemManager.getName()
        , systemManager.getURL()
    );
  }

  public static Link from(PatternManager patternManager) {
    return new Link(
        patternManager.getName()
        , patternManager.getUrl()
    );
  }
}
