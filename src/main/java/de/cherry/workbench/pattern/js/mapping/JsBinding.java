package de.cherry.workbench.pattern.js.mapping;

import de.cherry.workbench.pattern.js.Js4Workbench;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsBinding {

  private Map<String, JsBinding> fields;

  public static JsBinding create(JsMapping mapping, File web) throws Exception {
    File jsController = new File(web.toString() + "/" + mapping.src);
    String jsScript = new String(Files.readAllBytes(Paths.get(jsController.toString())));
    Js4Workbench js = new Js4Workbench();
    JsBinding fromMapping = fromMapping(mapping);
    JsBinding fromFile = js.loadBinding(jsScript);
    return mergeBindings(fromFile, fromMapping);
  }

  private static JsBinding mergeBindings(JsBinding fromFile, JsBinding fromMapping) {
    return null;
  }

  private static JsBinding fromMapping(JsMapping mapping) {
    JsBinding binding = new JsBinding();
    String temp = "";
    for (int i = 0; i < mapping.attr.size(); i++) {
      Attribute attribute = mapping.attr.get(i);
      if ("rv-each-item".equals(attribute.key)) {
        temp = attribute.value;
      }
      if (attribute.value.startsWith("item.")) {
        binding.addMapping(attribute.value.replace("item.", temp));
      } else {
        binding.addMapping(attribute.value);
      }
    }
    return binding;
  }

  private void addMapping(String value) {
    JsBinding temp = this;
    for (String path : value.split("\\.")) {
      JsBinding temp2 = temp.fields.get(path);
      if (temp2 == null) {
        temp2 = new JsBinding();
      }
      temp.fields.put(value, temp2);
      temp = temp2;
    }
  }
}
