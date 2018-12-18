package de.cherry.workbench.start;

import de.cherry.workbench.builder.DataNet;
import de.cherry.workbench.general.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRest {

  TempProject project = TempProject.getInstance();

  @GetMapping("project")
  public Project getProject() {
    return project.as.project;
  }

  @GetMapping("model")
  public DataNet getModel() {
    //todo
    return null;
  }


}
