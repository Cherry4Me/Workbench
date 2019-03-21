package de.cherry.workbench.clazz.event;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;

public class EventClazz implements MasterClazz {
  public String target;
  public String event;
  private String f;

  public EventClazz(String target, String event, File f) {
    this.target = target;
    this.event = event;
    this.setFile(f);
  }

  @Override
  public File getFile() {
    return new File(f);
  }

  @Override
  public void setFile(File f) {
    this.f = f.getAbsolutePath();
  }
}
