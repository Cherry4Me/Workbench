package de.cherry.workbench.system.api;

import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.clazz.rest.RestManager;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.file.FileTool;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.clazz2file.Clazz2FileDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ApiManager implements SystemManager {

  private CurrentProject project = CurrentProject.getInstance();
  private That that = That.getInstance();

  @Override
  public String getURL() {
    return "api.html";
  }

  @Override
  public String getName() {
    return "API";
  }

  @GetMapping("/api")
  public List<MasterClazz> getApi() {
    ArrayList<MasterClazz> clazzes = new ArrayList<>();
    String base = that.domain.current.path;
    Clazz2FileDTO clazz2FileDTO = new Clazz2FileDTO(base);
    ClazzManager clazzManager = project.findClazzManager(
        (cm) -> RestManager.class.isAssignableFrom(cm.getClass()));
    FileTool.walk(
        base,
        clazz2FileDTO,
        (inner, f) -> {
          clazzes.addAll(clazzManager.readClazz(f));
        }
    );

    return clazzes;
  }
}
