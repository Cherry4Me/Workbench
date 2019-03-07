package com.example.out.stream;

import java.util.function.Predicate;

public class GroesserFiveFilter implements Predicate<Integer> {
  @Override
  public boolean test(Integer integer) {
    return integer > 5;
  }
}
