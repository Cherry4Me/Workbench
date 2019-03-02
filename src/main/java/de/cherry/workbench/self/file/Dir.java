package de.cherry.workbench.self.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Dir {
  private Path start;
  private List<Dir> innerDirs = new ArrayList<>();
  private List<Fileable> innerEntitys = new ArrayList<>();

  public File getFile() {
    return start.toFile();
  }

  private Dir(Path start) {
    this.start = start;
  }

  public static Dir start(Path start) {
    return new Dir(start);
  }

  public Dir add(Fileable fileEntitys) {
    innerEntitys.add(fileEntitys);
    return this;
  }

  public Dir add(Function<Function<String, Dir>, Dir> innerFolder) {

    return this;
  }


  public Dir add(String name, Function<Dir, Dir>... inner) {
    Dir child = new Dir(Paths.get(start.toString() + File.separator + name));
    for (Function<Dir, Dir> dirDirFunction : inner) {
      dirDirFunction.apply(child);
    }
    innerDirs.add(child);
    return this;
  }

  public Dir addPackage(String name, Function<Dir, Dir>... inner) {
    String[] packages = name.split("\\.");
    Dir child = this;

    for (String aPackage : packages) {
      //todo
      Dir temp = new Dir(Paths.get(start.toString() + File.separator + aPackage));
      innerDirs.add(temp);
      child = temp;
    }
    return child;
  }


  public void build() throws IOException {
    Files.createDirectories(start);
    for (Fileable innerEntity : innerEntitys) {
      FileUtils.writeStringToFile(
          new File(start.toFile(), innerEntity.name())
          , innerEntity.build()
          , Charset.forName("UTF-8"));
    }
    for (Dir inner : innerDirs) {
      inner.build();
    }
  }

  public void delete() throws IOException {
    FileUtils.deleteDirectory(start.toFile());
  }


}
