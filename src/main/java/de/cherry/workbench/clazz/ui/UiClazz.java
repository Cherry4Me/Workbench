package de.cherry.workbench.clazz.ui;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;

public class UiClazz implements MasterClazz {
  public String title;
  public String bodyText;
  private String file;

  public UiClazz(String title, String bodyText, String file) {

    this.title = title;
    this.bodyText = bodyText;
    this.file = file;
  }

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
