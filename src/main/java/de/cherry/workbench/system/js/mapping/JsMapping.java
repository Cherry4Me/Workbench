package de.cherry.workbench.system.js.mapping;

import org.jsoup.nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class JsMapping {

  public String src;
  public List<Attribute> attr = new ArrayList<>();


  public static void getMapping(List<Node> childNodes, JsMapping jsMapping) {
    for (Node childNode : childNodes) {
      if ("script".equals(childNode.nodeName().toLowerCase())) {
        String src = childNode.attr("src");
        String[] path = src.split("/");
        String fileName = path[path.length - 1];
        if (fileName.startsWith("wb-")) {
          jsMapping.src = src;
        }
      }
      for (org.jsoup.nodes.Attribute attribute : childNode.attributes()) {
        if (attribute.getKey().startsWith("rv")) {
          jsMapping.attr.add(new Attribute(attribute.getKey(), attribute.getValue()));
        }
      }
      if (childNode.childNodeSize() > 0)
        getMapping(childNode.childNodes(), jsMapping);
    }
  }
}
