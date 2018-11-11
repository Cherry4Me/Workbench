package de.cherry.workbench.sapui;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SapUiBenchTest {

  @Test
  public void test() throws Exception {
    File manifestFile =
        new File("/Users/mbaaxur/Documents/gits/workbench/demo/src/main/resources/static/ShoppingCart/manifest.json");

    SapUiBench sapUiBench = new SapUiBench(manifestFile);
    sapUiBench.init();

  }

}