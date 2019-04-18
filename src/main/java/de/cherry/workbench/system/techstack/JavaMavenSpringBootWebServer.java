package de.cherry.workbench.system.techstack;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.domain.Project;
import de.cherry.workbench.meta.file.Dir;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

public class JavaMavenSpringBootWebServer implements TechnologyStack {
  @Override
  public void build(Project project) throws IOException {
    File path = new File(project.path);
    if (!path.exists())
      path.mkdir();
    if (!path.isDirectory() | !path.canWrite())
      throw new RuntimeException("nope");

    Pom pom = new Pom(project);

    Dir dir = Dir.start(path.toPath());
    dir.add("src",
        src -> src.add("main",
            main -> main.add("java",
                java -> {
                  String packageName = project.group;
                  new File(java.getFile().getAbsolutePath() + File.separator
                      + packageName.replace(".", File.separator)).mkdir();
                  return java;
                })
            ,
            main -> main.add("resources"),
            main -> main.add("webapp",
                webapp -> {
                  return webapp;
                })
        ),
        src -> src.add("test",
            test -> test.add("java")
        )
    )
        .add(pom)
        .build();
    createSpringMain(project);
  }

  private void createSpringMain(Project project) {

    String application = "Application";
    String args = "args";
    MethodSpec main = MethodSpec.methodBuilder("main")
        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
        .returns(void.class)
        .addParameter(String[].class, args)
        .addStatement("$T.run(" + application + ".class, $L)"
            , org.springframework.boot.SpringApplication.class, args)
        .build();

    TypeSpec helloWorld = TypeSpec.classBuilder(application)
        .addAnnotation(SpringBootApplication.class)
        .addModifiers(Modifier.PUBLIC)
        .addMethod(main)
        .build();

    JavaFile javaFile = JavaFile.builder(project.group, helloWorld)
        .build();

    That.getInstance().getJ().addClass(javaFile);
  }
}
