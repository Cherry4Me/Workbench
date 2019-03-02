package de.cherry.workbench.self;

import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.self.server.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRest {

  TempProject project = TempProject.getInstance();

  @GetMapping("project")
  public TypeSaveObject<Project> getProject() {
    return new TypeSaveObject<>(project.as.project);
  }


}
