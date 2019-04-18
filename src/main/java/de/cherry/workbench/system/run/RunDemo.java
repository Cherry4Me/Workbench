package de.cherry.workbench.system.run;

import de.cherry.workbench.meta.That;
import de.cherry.workbench.system.SystemManager;
import org.apache.maven.shared.invoker.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

@RestController
public class RunDemo implements SystemManager {
  @Override
  public String getURL() {
    return "/system/run.html";
  }

  @Override
  public String getName() {
    return "Demo Run";
  }

  @PostMapping("runit")
  public boolean runit() throws MavenInvocationException {

    String s = That.getInstance().get().path + File.separator + "pom.xml";
    InvocationRequest request = new DefaultInvocationRequest();
    request.setPomFile(new File(s));
    request.setGoals(Collections.singletonList("spring-boot:run"));

    Invoker invoker = new DefaultInvoker();
    invoker.setMavenHome(
        new File("/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3"));
    invoker.execute(request);
    return true;
  }
}
