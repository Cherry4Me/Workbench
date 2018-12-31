package de.cherry.workbench.mapping;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class JsMapping {

  private String src;
  private List<Attribute> attr = new ArrayList<>();


  public static void getMapping(List<Node> childNodes, JsMapping jsMapping) {
    for (Node childNode : childNodes) {
      if ("script".equals(childNode.nodeName().toLowerCase())) {
        String src = childNode.attr("src");
        String[] path = src.split("/");
        String fileName = path[path.length - 1];
        if (fileName.startsWith("wb-")) {
          jsMapping.setFileName(src);
        }
      }
      for (Attribute attribute : childNode.attributes()) {
        if (attribute.getKey().startsWith("rv")) {
          jsMapping.attr(attribute);
        }
      }
      if (childNode.childNodeSize() > 0)
        getMapping(childNode.childNodes(), jsMapping);
    }
  }

  public static void syncFile(JsMapping mapping) {
    throw new NotImplementedException();
  }

  public void setFileName(String src) {
    this.src = src;
  }

  public void attr(Attribute attribute) {
    attr.add(attribute);
  }

  public String getSrc() {
    return src;
  }

  public List<Attribute> getAttr() {
    return attr;
  }


}
