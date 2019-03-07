package de.cherry.workbench.system.impl;

import org.junit.Test;

import java.util.List;

public class SystemRestTest {

  @Test
  public void getSystems() {

    List<SystemDTO> ui = new SystemRest().getSystems("Ui");
    System.out.println(ui);
  }
}