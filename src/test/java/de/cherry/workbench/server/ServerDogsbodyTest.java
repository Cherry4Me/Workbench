package de.cherry.workbench.server;

import com.squareup.javapoet.*;
import de.cherry.workbench.general.Project;
import org.junit.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.reference.CtTypeReference;

import javax.lang.model.type.TypeMirror;
import java.io.File;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ServerDogsbodyTest {

  @Test
  public void create() throws Exception {
    File out = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out");
    ApplicationServer as = ServerDogsbody.create(out, new Project("com.example.out", "out"));
    CtClass classForRest = as.allSpoonClasses.getClasses().get(1);


    ClassName longClass = ClassName.get(Long.class);
    ClassName classForRestName = ClassName.get(classForRest.getPackage().getQualifiedName()
        , classForRest.getSimpleName());


    ParameterizedTypeName jpaRepoRef =
        ParameterizedTypeName.get(ClassName.get(JpaRepository.class), classForRestName, longClass);


    TypeSpec repository = TypeSpec
        .interfaceBuilder(classForRest.getSimpleName() + "Repository")
        .addSuperinterface(jpaRepoRef)
        .build();
    JavaFile javaFile = JavaFile
        .builder(as.project.getGroup(), repository)
        .build();
    as.addClass(javaFile);
    as.build();


  }
}