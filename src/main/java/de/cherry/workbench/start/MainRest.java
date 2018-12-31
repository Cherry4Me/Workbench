package de.cherry.workbench.start;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import de.cherry.workbench.builder.DataNet;
import de.cherry.workbench.builder.Edge;
import de.cherry.workbench.builder.Node;
import de.cherry.workbench.desinger.Page;
import de.cherry.workbench.general.Dir;
import de.cherry.workbench.general.Project;
import de.cherry.workbench.mapping.JsMapping;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class MainRest {

  TempProject project = TempProject.getInstance();

  @GetMapping("project")
  public Project getProject() {
    return project.as.project;
  }

  @GetMapping("pages")
  public List<String> getPages() throws IOException {
    Dir web = project.as.webDir.get();
    List<String> pathList = Files.walk(Paths.get(web.getFile().getAbsolutePath()))
        .filter(Files::isRegularFile)
        .filter((path -> "html".equals(FilenameUtils.getExtension(path.toString()))))
        .map(path -> path.toString().split("webapp")[1])
        .collect(Collectors.toList());
    return pathList;
  }

  @GetMapping("mapping")
  public JsMapping getMapping(@RequestParam("page") String page) throws IOException {
    Dir web = project.as.webDir.get();
    String pageFile = web.getFile().toString() + page;
    String html = new String(Files.readAllBytes(Paths.get(pageFile)));
    Document document = Jsoup.parse(html);
    JsMapping jsMapping = new JsMapping();
    JsMapping.getMapping(document.childNodes(), jsMapping);
    JsMapping.syncFile(jsMapping);
    return jsMapping;
  }


  @GetMapping("page")
  public String getPage(@RequestParam("page") String page) throws IOException {
    Dir web = project.as.webDir.get();
    String pageFile = web.getFile().toString() + page;
    String html = new String(Files.readAllBytes(Paths.get(pageFile)));
    Document document = Jsoup.parse(html);
    document.title();
    return document.body().html();
  }

  @PostMapping("page")
  public void savePage(@RequestParam("page") String pageUrl
      , @RequestBody Page page) throws IOException {
    Dir web = project.as.webDir.get();
    String pageFile = web.getFile().toString() + pageUrl;
    Path path = Paths.get(pageFile);
    String html = new String(Files.readAllBytes(path));
    Document document = Jsoup.parse(html);
    document.body().html(page.getBody());
    Files.write(path, document.html().getBytes());
  }


  @PostMapping("model")
  public void setModel(@RequestBody DataNet dataNet) {
    Map<String, Node> nodeMap = dataNet.nodes.stream().collect(Collectors.toMap(Node::getId, Node::giveMeThat));
    for (Node node : dataNet.nodes) {
      if ("Entity".equals(node.type)) {
        TypeSpec.Builder domainClass = TypeSpec.classBuilder(node.label);
        List<Edge> allEdgesToThatNode = getAllEdgesToThatNode(dataNet, node).collect(Collectors.toList());
        for (Edge edge : allEdgesToThatNode) {
          Node toAdd;
          boolean thisNodeHasTheOther;
          if (edge.to.equals(node.id)) {
            toAdd = nodeMap.get(edge.from);
            if ("1-n".equals(edge.label)) {
              thisNodeHasTheOther = false;
            } else {
              thisNodeHasTheOther = true;
            }
          } else {
            toAdd = nodeMap.get(edge.to);
            if ("n-1".equals(edge.label)) {
              thisNodeHasTheOther = false;
            } else {
              thisNodeHasTheOther = true;
            }
          }
          if ("Entity".equals(toAdd.type)) {
            //add Dependency
            if (!thisNodeHasTheOther) {
              ClassName className = ClassName.get("com.example.out.domain", toAdd.label);
              domainClass.addField(className, lowerFirstLetter(toAdd.label), Modifier.PRIVATE);
            }
          } else {
            //addField
            ClassName className = ClassName.get("java.lang", toAdd.type);
            domainClass.addField(className, lowerFirstLetter(toAdd.label), Modifier.PRIVATE);
          }
        }
        JavaFile domainClassFile = JavaFile
            .builder("com.example.out.domain", domainClass.build())
            .build();
        project.as.addClass(domainClassFile);
      }
    }
  }

  private String lowerFirstLetter(String simpleName) {
    return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
  }

  private Stream<Edge> getAllEdgesToThatNode(DataNet dataNet, Node node) {
    return dataNet.edges.stream().filter(o -> o.from.equals(node.id) || o.to.equals(node.id));
  }


  @GetMapping("model")
  public DataNet getModel() {
    int sequence = 0;
    TempProject project = TempProject.getInstance();
    DataNet dataNet = new DataNet();
    HashMap<String, String> node2Name = new HashMap<>();
    for (CtClass aClass : project.as.allSpoonClasses.getClasses()) {
      if ("domain".equals(aClass.getPackage().getSimpleName())) {
        String simpleName = aClass.getSimpleName();
        String id = node2Name.get(simpleName.toLowerCase());
        if (id == null) {
          id = "" + sequence++;
          node2Name.put(simpleName.toLowerCase(), id);
        }
        node2Name.put(simpleName.toLowerCase(), id);
        Node node = new Node();
        node.id = id;
        node.type = "Entity";
        node.label = simpleName;
        dataNet.nodes.add(node);
        List<CtField> fields = aClass.getFields();
        for (CtField ctField : fields) {
          Node field = new Node();
          Edge edge = new Edge();
          edge.id = "" + sequence++;
          String fieldType = ctField.getType().getSimpleName();
          if ("java.lang".equals(ctField.getType().getPackage().getSimpleName())) {
            field.id = "" + sequence++;
            field.type = fieldType;
            field.label = ctField.getSimpleName();
            dataNet.nodes.add(field);
            edge.to = field.id;
          } else {
            edge.label = "n-1";
            String to = node2Name.get(fieldType.toLowerCase());
            if (to == null) {
              to = "" + sequence++;
              node2Name.put(fieldType.toLowerCase(), to);
            }
            edge.to = to;
          }
          edge.from = node.id;
          dataNet.edges.add(edge);
        }
      }
    }
    return dataNet;
  }


}
