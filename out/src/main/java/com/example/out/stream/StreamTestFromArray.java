package com.example.out.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTestFromArray {

  public void test() {
    List<Integer> integers = Arrays.asList(2, 1, 9, 3, 7);
    List<Integer> kleinerAlsFuenf = integers.stream()
        .filter(integer -> integer < 5)
        .collect(Collectors.toList());
  }
}
