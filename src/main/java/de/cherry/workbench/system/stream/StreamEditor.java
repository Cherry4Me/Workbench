package de.cherry.workbench.system.stream;

import de.cherry.workbench.clazz.stream.StreamManager;
import de.cherry.workbench.self.TempProject;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.clazzeditor.Clazz2Edit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamEditor implements SystemManager {

  TempProject project = TempProject.getInstance();

  @PostMapping("/streamInfo")
  public TypeSaveObject getClazzWithData(@RequestBody Clazz2Edit className) throws Exception {
    return null;
  }


  @Override
  public String getURL() {
    return "streamEditor.html";
  }

  @Override
  public String getName() {
    return "Stream Editor";
  }

  @Override
  public boolean isItTheRightOneFor(String clazz) {
    return new StreamManager().getClazzName().equals(clazz);
  }
}
