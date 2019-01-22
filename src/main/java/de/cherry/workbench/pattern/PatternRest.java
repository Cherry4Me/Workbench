package de.cherry.workbench.pattern;

import de.cherry.workbench.clazz.ClassAndClazz;
import de.cherry.workbench.interpreter.Uiable;
import de.cherry.workbench.start.TempProject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PatternRest {

  TempProject project = TempProject.getInstance();

  @PostMapping("/getState")
  public Uiable getState(@RequestBody ClassAndClazz classAndClazz) {
    return null;
  }

}
