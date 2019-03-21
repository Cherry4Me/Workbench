package de.cherry.workbench.clazz.jq;

import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.clazz.event.EventManager;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class JqManagerTest {

  @Test
  public void readClazz() {
    File file = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out/src/main/webapp/call.js");
    EventManager eventManager = new EventManager();
    List<? extends MasterClazz> masterClazzes = eventManager.readClazz(file);
    System.out.println(masterClazzes);
  }

}