package de.cherry.workbench.clazz.rest;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RestClazz implements MasterClazz {

  public List<Service> services = new ArrayList<>();
  private String file;

  @Override
  public File getFile() {
    return new File(file);
  }

  @Override
  public void setFile(File f) {
    this.file = f.getAbsolutePath();
  }

  @Override
  public void save() {

  }
}
