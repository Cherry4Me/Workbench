package de.cherry.workbench.meta.server;

import de.cherry.workbench.meta.domain.Project;
import de.cherry.workbench.meta.file.Fileable;

public class Pom implements Fileable {

  private Project project;

  public Pom(Project project) {
    this.project = project;
  }

  @Override
  public String name() {
    return "pom.xml";
  }

  @Override
  public String build() {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
        "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
        "  <modelVersion>4.0.0</modelVersion>\n" +
        "\n" +
        "  <groupId>" + project.group + "</groupId>\n" +
        "  <artifactId>" + project.name + "</artifactId>\n" +
        "  <version>0.0.1-SNAPSHOT</version>\n" +
        "  <packaging>jar</packaging>\n" +
        "\n" +
        "  <name>" + project.name + "</name>\n" +
        "  <description>Demo project for Spring Boot</description>\n" +
        "\n" +
        "  <parent>\n" +
        "    <groupId>org.springframework.boot</groupId>\n" +
        "    <artifactId>spring-boot-starter-parent</artifactId>\n" +
        "    <version>2.1.0.RELEASE</version>\n" +
        "    <relativePath/> <!-- lookup parent from repository -->\n" +
        "  </parent>\n" +
        "\n" +
        "  <properties>\n" +
        "    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
        "    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>\n" +
        "    <javaDir.version>1.8</javaDir.version>\n" +
        "  </properties>\n" +
        "\n" +
        "\n" +
        "  <dependencies>\n" +
        "    <dependency>\n" +
        "      <groupId>org.springframework.boot</groupId>\n" +
        "      <artifactId>spring-boot-starter-actuator</artifactId>\n" +
        "    </dependency>\n" +
        "    <dependency>\n" +
        "      <groupId>org.springframework.boot</groupId>\n" +
        "      <artifactId>spring-boot-starter-data-jpa</artifactId>\n" +
        "    </dependency>\n" +
        "    <dependency>\n" +
        "      <groupId>org.springframework.boot</groupId>\n" +
        "      <artifactId>spring-boot-starter-web</artifactId>\n" +
        "    </dependency>\n" +
        "\n" +
        "    <dependency>\n" +
        "      <groupId>org.springframework.boot</groupId>\n" +
        "      <artifactId>spring-boot-devtools</artifactId>\n" +
        "      <scope>runtime</scope>\n" +
        "    </dependency>\n" +
        "    <dependency>\n" +
        "      <groupId>com.h2database</groupId>\n" +
        "      <artifactId>h2</artifactId>\n" +
        "      <scope>runtime</scope>\n" +
        "    </dependency>\n" +
        "    <dependency>\n" +
        "      <groupId>org.springframework.boot</groupId>\n" +
        "      <artifactId>spring-boot-starter-test</artifactId>\n" +
        "      <scope>test</scope>\n" +
        "    </dependency>\n" +
        "  </dependencies>\n" +
        "\n" +
        "  <build>\n" +
        "    <plugins>\n" +
        "      <plugin>\n" +
        "        <groupId>org.springframework.boot</groupId>\n" +
        "        <artifactId>spring-boot-maven-plugin</artifactId>\n" +
        "      </plugin>\n" +
        "    </plugins>\n" +
        "  </build>\n" +
        "\n" +
        "\n" +
        "</project>\n";
  }
}
