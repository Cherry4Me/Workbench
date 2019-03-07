package com.example.out.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamInlineReturnTest {

  public List<Integer> test() {
    return Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        .filter(new GroesserFiveFilter())
        .map(integer -> integer + 2)
        .collect(Collectors.toList());
  }
}