package de.cherry.workbench.clazz.repository;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;

public class ReositoryClazz implements MasterClazz {

  private String file;

  @Override
  public File getFile() {
    return new File(file);
  }

  @Override
  public void setFile(File f) {
    this.file = f.getAbsolutePath();
  }

  public String model;
}
