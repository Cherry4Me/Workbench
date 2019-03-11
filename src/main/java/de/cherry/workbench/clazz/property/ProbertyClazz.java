package de.cherry.workbench.clazz.property;

import de.cherry.workbench.clazz.MasterClazz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProbertyClazz implements MasterClazz {
  public Map<String, GetterAndSetter> fields = new HashMap<>();
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
