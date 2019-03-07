package com.example.out.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTestParameter {

  public void test(Stream<Integer> integers) {

    List<Integer> kleinerAlsFuenf = integers
        .filter(integer -> integer < 5)
        .collect(Collectors.toList());
  }
}
