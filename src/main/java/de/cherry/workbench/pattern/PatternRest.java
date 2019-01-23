package de.cherry.workbench.pattern;

import de.cherry.workbench.clazz.ClassAndClazz;
import de.cherry.workbench.general.start.TempProject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class PatternRest {

  TempProject project = TempProject.getInstance();

  @PostMapping("/getState")
  public HashMap<String, List<MasterPattern>> getState(@RequestBody ClassAndClazz classAndClazz) {
    HashMap<String, List<MasterPattern>> patternData4pattern = new HashMap<>();


    return null;

  }

}
