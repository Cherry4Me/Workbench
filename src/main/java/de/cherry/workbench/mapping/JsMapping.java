package de.cherry.workbench.mapping;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  public static void syncFile(JsMapping mapping, File web) throws ScriptException, IOException {
    File jsController = new File(web.toString() + "/" + mapping.src);
    String jsScript = new String(Files.readAllBytes(Paths.get(jsController.toString())));


    ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
    ScriptContext context = engine.getContext();
    StringWriter writer = new StringWriter();
    context.setWriter(writer);

    engine.eval("print('Welocme to java worldddd')");

    String output = writer.toString();

    System.out.println("Script output: " + output);
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
