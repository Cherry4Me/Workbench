package de.cherry.workbench.meta.server;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.cherry.workbench.meta.domain.Project;
import de.cherry.workbench.meta.file.Dir;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ApplicationServer {




  public AtomicReference<Dir> javaDir = new AtomicReference<>();
  public AtomicReference<Dir> webDir = new AtomicReference<>();
  public Pom pom = null;

  public Project project;
  public File location;

  public static ApplicationServer create(Project project) throws Exception {
    File dir = new File(project.path);
    if (!dir.isDirectory() | !dir.canWrite())
      throw new RuntimeException("nope");
    ApplicationServer server = new ApplicationServer();


    server.project = project;
    server.location = dir;
    server.init();

    return server;
  }


  public void init() throws IOException {

    pom = new Pom(project);

    Dir dir = Dir.start(location.toPath());
    dir.add("src",
        src -> src.add("main",
            main -> main.add("java",
                java -> {
                  javaDir.set(java);
                  String packageName = project.group;
                  new File(java.getFile().getAbsolutePath() + File.separator
                      + packageName.replace(".", File.separator)).mkdir();
                  return java;
                })
            ,
            main -> main.add("resources"),
            main -> main.add("webapp",
                webapp -> {
                  webDir.set(webapp);
                  return webapp;
                })
        ),
        src -> src.add("test",
            test -> test.add("java")
        )
    )
        .add(pom)
        .build();
    createSpringMain();

  }

  private void createSpringMain() {

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

    JavaFile javaFile = JavaFile.builder(this.project.group, helloWorld)
        .build();

    //todo this.addClass(javaFile);
  }


  protected ApplicationServer() {

  }

}
