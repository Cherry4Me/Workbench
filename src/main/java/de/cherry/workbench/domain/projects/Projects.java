package de.cherry.workbench.domain.projects;

import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.domain.DomainManager;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.domain.MyDomain;
import de.cherry.workbench.meta.domain.Project;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Projects implements DomainManager {
  That that = That.getInstance();

  @GetMapping("project")
  public TypeSaveObject<Project> getProject() {
    return new TypeSaveObject<>(that.domain.current);
  }

  MyDomain domain = MyDomain.getInstance();

  @GetMapping("projects")
  public List<Project> getProjects() {
    return domain.projects;
  }

  @Override
  public String getName() {
    return "Projects";
  }

  @Override
  public String getURL() {
    return "./projects.html";
  }
}
