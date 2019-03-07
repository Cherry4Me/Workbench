package de.cherry.workbench.general;

import de.cherry.workbench.self.file.Dir;
import de.cherry.workbench.self.file.TxtFile;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirTest {

  @Test
  public void start() throws IOException {
    Path out = Paths.get("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out");
    Dir start = Dir.start(out);
    start.delete();

    start
        .add("test")
        .add("test2")
        .add("inner",
            x -> x.add("innerinner"),
            x -> x.add("test"),
            x -> x.add(new TxtFile())
        )
        .add(new TxtFile());
    start.build();

    System.out.println("start");
  }

  @Test
  public void delete() throws IOException {

    Dir start = Dir.start(Paths.get("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out"));
    start.delete();
  }
}
