package de.cherry.workbench.meta.file;

import de.cherry.workbench.system.clazz2file.Clazz2FileDTO;

import java.io.File;
import java.util.function.BiConsumer;

public class FileTool {
  public static void walk(String path, Clazz2FileDTO clazz2FileDTO, BiConsumer<Clazz2FileDTO, File> forEach) {
    File root = new File(path);
    File[] list = root.listFiles();
    if (list == null)
      return;
    for (File f : list)
      if (!f.getName().startsWith(".")) {
        Clazz2FileDTO inner = new Clazz2FileDTO(f.getName());
        if (f.isDirectory()) {
          if (!f.getName().equals("target")) {
            walk(f.getAbsolutePath()
                , inner
                , forEach);
          }
        } else {
          forEach.accept(inner, f);
        }
        clazz2FileDTO.inner.add(inner);
      }
  }
}
