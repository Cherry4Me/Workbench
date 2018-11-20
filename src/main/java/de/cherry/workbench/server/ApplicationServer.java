package de.cherry.workbench.server;

import de.cherry.workbench.general.Dir;
import de.cherry.workbench.general.Project;
import de.cherry.workbench.general.TxtFile;

import java.io.File;
import java.io.IOException;

public class ApplicationServer {

  private Project project;
  private File location;

  protected ApplicationServer() {

  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Project getProject() {
    return project;
  }

  public void setLocation(File location) {
    this.location = location;
  }

  public File getLocation() {
    return location;
  }

  public void build() throws Exception {
    Pom pom = new Pom();


    Dir dir = Dir.start(location.toPath());
    dir.delete();
    dir.add("src",
        src -> src.add("main",
            main -> main.add("java",
                java -> java.add("de",
                    de -> de.add("test",
                        test -> test
                            .add("out")
                    )
                )
            ),
            x1 -> x1.add("resources")
        ),
        src -> src.add("test",
            test -> test.add("java",
                java -> java.add("de",
                    de -> de.add("test",
                        innerTest -> innerTest
                            .add("out")
                    )
                )
            )
        )
    )
        .add(pom)
        .build();

  }
}
