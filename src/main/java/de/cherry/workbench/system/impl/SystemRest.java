package de.cherry.workbench.system.impl;

import de.cherry.workbench.clazz.impl.ClassAndClazz;
import de.cherry.workbench.self.TempProject;
import de.cherry.workbench.system.MasterSystem;
import de.cherry.workbench.system.clazzeditor.ClazzEditor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class SystemRest {

  TempProject project = TempProject.getInstance();

  @PostMapping("/getPattern")
  public HashMap<String, List<MasterSystem>> getState(@RequestBody ClassAndClazz classAndClazz) {
    HashMap<String, List<MasterSystem>> systemData4systems = new HashMap<>();
    return null;
  }

  @GetMapping("/getSystems/{clazz}")
  public List<SystemDTO> getSystems(@PathVariable("clazz") String clazz) {
    return project.systemManagers
        .stream()
        .filter(systemFinder -> {
          if (systemFinder.getName().equals(new ClazzEditor().getName())) {
            return true;
          }
          return systemFinder.isItTheRightOneFor(clazz);
        })
        .map(SystemDTO::from)
        .collect(Collectors.toList());
  }

  @GetMapping("/getSystems")
  public List<SystemDTO> getSystems() {
    return project.systemManagers
        .stream()
        .map(SystemDTO::from)
        .collect(Collectors.toList());
  }

}
