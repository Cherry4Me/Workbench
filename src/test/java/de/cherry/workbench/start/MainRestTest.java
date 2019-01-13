package de.cherry.workbench.start;

import de.cherry.workbench.mapping.JsBinding;
import org.junit.Test;

import java.io.IOException;

public class MainRestTest {

  @Test
  public void getPages() throws IOException {

    MainRest mainRest = new MainRest();
    mainRest.getPages();
  }

  @Test
  public void getMapping() throws Exception {
    MainRest mainRest = new MainRest();
    JsBinding binding = mainRest.getMapping("/test.html");

    System.out.println(binding);


  }


}