package de.cherry.workbench.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import org.junit.Test;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class DomainBuilderTest {

  @Test
  public void buildNet() throws IOException {
    String domainNet = "{ \"edges\": [{ \"from\": \"3a0f37ec-7b17-4cf5-964c-ebe8fc11b742\", \"to\": \"90192d1e-3c8f-4da9-be71-d8c1343c6416\", \"label\": \"\", \"id\": \"20b35d90-96e4-4d35-b802-f59d8c3fb7ac\" }, { \"from\": \"90192d1e-3c8f-4da9-be71-d8c1343c6416\", \"to\": \"41f5adae-b659-4bad-9f07-1363ae64bbe0\", \"label\": \"n-1\", \"id\": \"c72e2886-0589-4852-80b3-bce51534f351\" }, { \"from\": \"912eca75-8b69-4567-b5ba-2587a45293a5\", \"to\": \"41f5adae-b659-4bad-9f07-1363ae64bbe0\", \"label\": \"\", \"id\": \"0fa55875-deec-4d22-a407-9957bb8a337d\" }, { \"from\": \"8f1d5e78-5090-42b2-be9e-2b439b139666\", \"to\": \"41f5adae-b659-4bad-9f07-1363ae64bbe0\", \"label\": \"\", \"id\": \"de79f4fa-c532-4be0-b5ec-cfec9532ec21\" }, { \"from\": \"41f5adae-b659-4bad-9f07-1363ae64bbe0\", \"to\": \"9aa8f4b4-1c59-400e-b118-b4f3ac88844d\", \"label\": \"\", \"id\": \"45fc780f-9a03-4219-9fbb-41ff08765120\" }], \"nodes\": [{ \"id\": \"41f5adae-b659-4bad-9f07-1363ae64bbe0\", \"x\": -73.75, \"y\": -3.5, \"label\": \"User\", \"type\": \"Entity\", \"shape\": \"box\" }, { \"id\": \"90192d1e-3c8f-4da9-be71-d8c1343c6416\", \"x\": 3.25, \"y\": -175.5, \"label\": \"Note\", \"type\": \"Entity\", \"shape\": \"box\" }, { \"id\": \"3a0f37ec-7b17-4cf5-964c-ebe8fc11b742\", \"x\": -75.75, \"y\": -103.5, \"label\": \"Text\", \"type\": \"String\", \"color\": \"#EC7063\", \"shape\": \"ellipse\" }, { \"id\": \"8f1d5e78-5090-42b2-be9e-2b439b139666\", \"x\": -109.75, \"y\": 127.5, \"label\": \"Name\", \"type\": \"String\", \"color\": \"#EC7063\", \"shape\": \"ellipse\" }, { \"id\": \"9aa8f4b4-1c59-400e-b118-b4f3ac88844d\", \"x\": -7.75, \"y\": 72.5, \"label\": \"Smart\", \"type\": \"Boolean\", \"color\": \"#C39BD3\", \"shape\": \"ellipse\" }, { \"id\": \"912eca75-8b69-4567-b5ba-2587a45293a5\", \"x\": -175.75, \"y\": 59.5, \"label\": \"Age\", \"type\": \"Integer\", \"color\": \"#7BE141\", \"shape\": \"ellipse\" }] }";
    ObjectMapper objectMapper = new ObjectMapper();
    DataNet dataNet = objectMapper.readValue(domainNet, DataNet.class);


    Map<String, Node> nodeMap = dataNet.nodes.stream().collect(Collectors.toMap(Node::getId, Node::getThat));
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
              ClassName className = ClassName.get("com.example.out", toAdd.label);
              domainClass.addField(className, lowerFirstLetter(toAdd.label), Modifier.PRIVATE);
            }
          } else {
            //addField
            ClassName className = ClassName.get("java.lang", toAdd.type);
            domainClass.addField(className, lowerFirstLetter(toAdd.label), Modifier.PRIVATE);
          }
        }

        JavaFile domainClassFile = JavaFile
            .builder("com.example.out", domainClass.build())
            .build();
        System.out.println(domainClassFile.toString());
        System.out.println("-------------------------------------------------------------");

      }
    }
    System.out.println("ende");


  }

  private String lowerFirstLetter(String simpleName) {
    return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
  }

  private Stream<Edge> getAllEdgesToThatNode(DataNet dataNet, Node node) {
    return dataNet.edges.stream().filter(o -> o.from.equals(node.id) || o.to.equals(node.id));
  }

}