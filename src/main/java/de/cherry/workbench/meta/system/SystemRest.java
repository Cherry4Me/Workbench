package de.cherry.workbench.meta.system;

import de.cherry.workbench.meta.clazz.ClassAndClazz;
import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.system.MasterSystem;
import de.cherry.workbench.pattern.clazzeditor.ClazzEditor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class SystemRest {

  CurrentProject project = CurrentProject.getInstance();

  @PostMapping("/getPattern")
  public HashMap<String, List<MasterSystem>> getState(@RequestBody ClassAndClazz classAndClazz) {
    HashMap<String, List<MasterSystem>> systemData4systems = new HashMap<>();
    return null;
  }

  @GetMapping("/getSystems/{clazz}")
  public List<Link> getSystems(@PathVariable("clazz") String clazz) {
    return project.patternManagers
        .stream()
        .filter(patternManager -> {
          if (patternManager.getName().equals(new ClazzEditor().getName())) {
            return true;
          }
          return patternManager.isPatternStartableForClazz(clazz);
        })
        .map(Link::from)
        .collect(Collectors.toList());
  }

  @GetMapping("/getSystems")
  public List<Link> getSystems() {
    return project.systemManagers
        .stream()
        .map(Link::from)
        .collect(Collectors.toList());
  }

}
