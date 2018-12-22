package de.cherry.workbench.start;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class MainRestTest {

  @Test
  public void getPages() throws IOException {

    MainRest mainRest = new MainRest();
    mainRest.getPages();
  }
}