package com.example.out.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamInlineTest {

  public void test() {
    List<Integer> kleinerAlsFuenf = Stream.of(1, 2,3,4,5,6,7,8,9,10)
        .filter(integer -> integer < 5)
        .collect(Collectors.toList());
  }
}
