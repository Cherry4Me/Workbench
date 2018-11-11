package de.cherry.workbench.general;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileCollector {

  private File directory;
  private List<File> out = new ArrayList<>();
  private Function<File, Boolean> filter;


  private FileCollector(File directory, Function<File, Boolean> filter) {
    this.directory = directory;
    this.filter = filter;
  }


  public static List<File> in(File directory, Function<File, Boolean> filter) {
    FileCollector fileCollector = new FileCollector(directory, filter);
    if (directory.isDirectory()) {
      fileCollector.find(directory);
    } else {
      throw new IllegalArgumentException(directory.getAbsoluteFile() + " is not a directory!");
    }
    return fileCollector.out;
  }

  private void find(File file) {
    System.out.println("Searching directory ... " + file.getAbsoluteFile());
    for (File temp : file.listFiles()) {
      if (temp.isDirectory()) {
        find(temp);
      } else {
        if (temp.isFile() && filter.apply(temp))
          out.add(temp);
      }
    }

  }
}
