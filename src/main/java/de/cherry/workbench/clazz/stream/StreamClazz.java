package de.cherry.workbench.clazz.stream;

import de.cherry.workbench.clazz.MasterClazz;
import spoon.reflect.cu.SourcePosition;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class StreamClazz implements MasterClazz {

  public HashMap<SourcePosition, List<String>> calls = new HashMap<>();

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
