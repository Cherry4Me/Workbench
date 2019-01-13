package de.cherry.workbench.stream;

import java.util.stream.Stream;

public interface Flow<T> extends Stream<T> {
  Stream<T> stream();


  //Watch out with Double cast. Check the type in method or restrict it via generic
  default Flow<T> biggerThanFour() {
    return Duck.typing(stream().filter(i -> ((Double) i > 4)), Flow.class);
  }

  //Watch out with Double cast. Check the type in method or restrict it via generic
  //Another method
  default Flow<T> biggerThanFourteen() {
    return Duck.typing(stream().filter(i -> ((Double) i > 14)), Flow.class);
  }
}
