package de.cherry.workbench.clazz.ajax;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;

public class AjaxClazz implements MasterClazz {

  public String type;
  public String url;
  private String file;

  @Override
  public File getFile() {
    return new File(file);
  }

  @Override
  public void setFile(File f) {
    this.file = f.getAbsolutePath();
  }
}
