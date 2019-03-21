package de.cherry.workbench.system.impl;

import de.cherry.workbench.meta.system.Link;
import de.cherry.workbench.meta.system.SystemRest;
import org.junit.Test;

import java.util.List;

public class SystemRestTest {

  @Test
  public void getSystems() {

    List<Link> ui = new SystemRest().getSystems("Ui");
    System.out.println(ui);
  }
}