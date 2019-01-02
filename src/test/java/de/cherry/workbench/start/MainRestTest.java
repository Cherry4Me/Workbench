package de.cherry.workbench.start;

import de.cherry.workbench.mapping.JsMapping;
import org.junit.Test;

import javax.script.ScriptException;
import java.io.IOException;

public class MainRestTest {

  @Test
  public void getPages() throws IOException {

    MainRest mainRest = new MainRest();
    mainRest.getPages();
  }

  @Test
  public void getMapping() throws IOException, ScriptException {
    MainRest mainRest = new MainRest();
    JsMapping jsMapping = mainRest.getMapping("/test.html");

    System.out.println(jsMapping);


  }


}