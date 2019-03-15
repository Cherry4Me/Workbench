package de.cherry.workbench.clazz.ajax;

import de.cherry.workbench.clazz.MasterClazz;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class AjaxManagerTest {

  @Test
  public void readClazz() {

    File file = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out/src/main/webapp/call.js");
    AjaxManager ajaxManager = new AjaxManager();
    List<? extends MasterClazz> masterClazzes = ajaxManager.readClazz(file);
    System.out.println(masterClazzes);
  }
}