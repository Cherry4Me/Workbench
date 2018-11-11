package de.cherry.workbench.sapui;

import de.cherry.workbench.sapui.controller.Databinding;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class DatabindingTest {

  @Test
  public void findInString() throws Exception {
    File file =
        new File("/Users/mbaaxur/Documents/gits/workbench/demo/src/main/resources/static/ShoppingCart/view/Cart.view.xml");

    HashMap<String, Set<String>> model = Databinding.findInString(file);

  }


}