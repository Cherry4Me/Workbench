package de.cherry.workbench.domain.projects;

import de.cherry.workbench.domain.DomainManager;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.domain.MyDomain;
import de.cherry.workbench.meta.domain.Project;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.system.techstack.JavaMavenSpringBootWebServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class Projects implements DomainManager {
  That that = That.getInstance();

  @GetMapping("project")
  public TypeSaveObject<Project> getProject() {
    return new TypeSaveObject<>(that.get());
  }

  @PostMapping("currentProject")
  public boolean setCurrentProject(@RequestBody Project newCurrent) {
    that.set(newCurrent);
    return true;
  }

  @GetMapping("projects")
  public List<Project> getProjects() {
    return MyDomain.getInstance().projects;
  }

  @PostMapping("createProject")
  public boolean createProject(@RequestBody ToCreate toCreate) throws IOException {
    Project project = new Project();
    project.name = toCreate.name;
    project.path = toCreate.path;
    project.group = toCreate.group;
    that.set(project);
    if (toCreate.template.equals("Java-Maven-Spring-Boot-Web-Server")){
      JavaMavenSpringBootWebServer template = new JavaMavenSpringBootWebServer();
      template.build(project);
    }
    return false;
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
