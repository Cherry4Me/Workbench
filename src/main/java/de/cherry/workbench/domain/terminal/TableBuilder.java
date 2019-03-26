package de.cherry.workbench.domain.terminal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

public class TableBuilder implements Builder {
  private List<String> theClass = new ArrayList<>();
  private List<Map<String, String>> table = new ArrayList<>();

  @Override
  public void add(String line) {
    if (theClass.isEmpty())
      buildClass(line);
    else
      addRow(line);
  }

  private void addRow(String line) {
    List<String> split = greedySplit(" ", line);
    Map<String, String> object = new LinkedHashMap<>();
    for (int i = 0; i < split.size(); i++) {
      String field = String.valueOf(i);
      if (theClass.size() > i)
        field = theClass.get(i);
      String value = split.get(i);
      object.put(field, value);
    }
    table.add(object);
  }

  private void buildClass(String line) {
    List<String> split = greedySplit(" ", line);
    theClass.addAll(split);
  }

  private List<String> greedySplit(String seperator, String line) {
    ArrayList<String> strings = new ArrayList<>();
    String string = "";
    for (String achar : line.split("")) {
      if (achar.equals(seperator)) {
        if (!string.equals("")) {
          strings.add(string);
          string = "";
        }
      } else {
        string = string + achar;
      }
    }
    return strings;
  }

  @Override
  public String getString() {
    try {
      return new ObjectMapper().writeValueAsString(table);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
