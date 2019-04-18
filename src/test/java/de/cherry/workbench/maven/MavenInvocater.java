package de.cherry.workbench.maven;

import org.apache.maven.shared.invoker.*;
import org.junit.Test;

import java.io.File;
import java.util.Collections;

public class MavenInvocater {

  @Test
  public void test() throws MavenInvocationException {
    InvocationRequest request = new DefaultInvocationRequest();
    request.setPomFile(new File("/Users/mbaaxur/Documents/JavaTest/workbenches/out/pom.xml"));
    request.setGoals(Collections.singletonList("spring-boot:run"));

    Invoker invoker = new DefaultInvoker();
    invoker.setMavenHome(
        new File("/Applications/IntelliJ IDEA CE.app/Contents/plugins/maven/lib/maven3/bin/mvn"));
    invoker.execute(request);
  }
}
