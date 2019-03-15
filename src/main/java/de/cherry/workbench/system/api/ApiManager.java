package de.cherry.workbench.system.api;

import de.cherry.workbench.TempProject;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.clazz.rest.RestManager;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.clazz2file.Clazz2FileDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static de.cherry.workbench.TempProject.walk;

@RestController
public class ApiManager implements SystemManager {

  private TempProject project = TempProject.getInstance();

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
    String base = project.as.location.getAbsolutePath();
    Clazz2FileDTO clazz2FileDTO = new Clazz2FileDTO(base);
    ClazzManager clazzManager = project.findClazzManager(
        (cm) -> RestManager.class.isAssignableFrom(cm.getClass()));
    walk(
        base,
        clazz2FileDTO,
        (inner, f) -> {
          if (clazzManager.detect(f)) {
            clazzes.addAll(clazzManager.readClazz(f));
          }
        }
    );

    return clazzes;
  }
}
