package de.cherry.workbench.server;

import de.cherry.workbench.general.Fileable;

public class Pom  implements Fileable {
  @Override
  public String name() {
    return "pom.xml";
  }

  @Override
  public String build() {
    return null;
  }
}
