package de.cherry.workbench.stream;

import org.junit.Test;

import java.util.stream.Stream;

public class FlowTest {

  @Test
  public void biggerThanFour() {
    Duck.typing(Stream.of(12.0, 23.3, 2.0), Flow.class).biggerThanFour().forEach(System.out::println);
  }
}