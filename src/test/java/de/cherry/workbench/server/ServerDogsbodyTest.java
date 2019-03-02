package de.cherry.workbench.server;

import com.squareup.javapoet.*;
import de.cherry.workbench.self.server.ApplicationServer;
import de.cherry.workbench.self.server.Project;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spoon.reflect.declaration.CtClass;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ServerDogsbodyTest {

  @Test
  public void create() throws Exception {
    File out = new File("/Users/mbaaxur/Documents/gits/WorkbenchCherry/out");
    ApplicationServer as = ApplicationServer.create(out, new Project("com.example.out", "out"));
    CtClass classForRest = as.allSpoonClasses.getClasses().get(1);


    ClassName longClass = ClassName.get(Long.class);
    String simpleName = classForRest.getSimpleName();
    ClassName classForRestName = ClassName.get(classForRest.getPackage().getQualifiedName()
        , simpleName);


    ParameterizedTypeName jpaRepoRef =
        ParameterizedTypeName.get(ClassName.get(JpaRepository.class), classForRestName, longClass);


    String repoName = simpleName + "Repository";
    TypeSpec repository = TypeSpec
        .interfaceBuilder(repoName)
        .addSuperinterface(jpaRepoRef)
        .build();
    JavaFile javaFile = JavaFile
        .builder(as.project.group, repository)
        .build();
    as.addClass(javaFile);


    ClassName repoClassName = ClassName.get(classForRest.getPackage().getQualifiedName()
        , repository.name);

    FieldSpec repoField = FieldSpec
        .builder(repoClassName, lowerFirstLetter(repoName), Modifier.PRIVATE)
        .addAnnotation(Autowired.class)
        .build();

    ParameterizedTypeName typeList =
        ParameterizedTypeName.get(ClassName.get(List.class), classForRestName);


    MethodSpec getAll = MethodSpec
        .methodBuilder("retrieveAll" + simpleName)
        .addModifiers(Modifier.PUBLIC)
        .returns(typeList)
        .addAnnotation(getGetAnnotation("\"/$S\"", simpleName))
        .addStatement("return $N.findAll()", repoField.name)
        .build();

    ParameterSpec idParam = ParameterSpec
        .builder(long.class, "id")
        .addAnnotation(PathVariable.class)
        .build();


    MethodSpec getOne = MethodSpec
        .methodBuilder("retrieve" + simpleName)
        .addAnnotation(getGetAnnotation("\"/$N/{id}\"", simpleName))
        .addModifiers(Modifier.PUBLIC)
        .returns(classForRestName)
        .addParameter(idParam)
        .addStatement("$T<$N> $N = $N.findById($N)"
            , Optional.class, classForRestName.simpleName(), idParam.name, repoField.name, idParam.name)
        .addStatement("if (!$N.isPresent()) throw new IllegalArgumentException($S + $N)",
            idParam.name, "id-", idParam.name)
        .addStatement("return $N.get()", idParam.name)
        .build();

    MethodSpec delete = MethodSpec
        .methodBuilder("delete" + simpleName)
        .addAnnotation(getDeleteAnnotation("\"/$N/{id}\"", simpleName))
        .addModifiers(Modifier.PUBLIC)
        .addParameter(idParam)
        .addStatement("$N.deleteById($N)", repoField.name, idParam.name)
        .build();

    AnnotationSpec postAnnotation = AnnotationSpec
        .builder(PostMapping.class)
        .addMember("name", "\"/$N\"", lowerFirstLetter(simpleName))
        .build();

    ParameterSpec objectParam = ParameterSpec
        .builder(classForRestName, lowerFirstLetter(simpleName))
        .addAnnotation(RequestBody.class)
        .build();

    ParameterizedTypeName responseEntity =
        ParameterizedTypeName.get(ClassName.get(ResponseEntity.class), ClassName.get(Object.class));


    String savedObject = "saved" + simpleName;


    //todo works just with ID
    MethodSpec create = MethodSpec
        .methodBuilder("create" + simpleName)
        .returns(responseEntity)
        .addAnnotation(postAnnotation)
        .addModifiers(Modifier.PUBLIC)
        .addParameter(objectParam)
        .addStatement("$N $N = $N.save($N)",
            simpleName, savedObject, repoField.name, objectParam.name)
        .addStatement(
            "$T location = $T\n" +
                "        .fromCurrentRequest()\n" +
                "        .path(\"/{id}\")\n" +
                "        .buildAndExpand(\n" +
                "            $N\n" +
                "                .getId())\n" +
                "        .toUri()",
            URI.class, ServletUriComponentsBuilder.class, savedObject
        )
        .addStatement("return $T.created(location).build()",
            ResponseEntity.class)
        .build();

    AnnotationSpec putAnnotation = AnnotationSpec
        .builder(PutMapping.class)
        .addMember("name", "\"/$N/{id}\"", lowerFirstLetter(simpleName))
        .build();

    MethodSpec update = MethodSpec
        .methodBuilder("update" + simpleName)
        .returns(responseEntity)
        .addAnnotation(putAnnotation)
        .addParameters(Arrays.asList(objectParam, idParam))
        .addStatement("$T<$N> $N = $N.findById($N)"
            , Optional.class, classForRestName.simpleName(), idParam.name, repoField.name, idParam.name)
        .addStatement("if (!$N.isPresent()) return $T.notFound().build()",
            idParam.name, ResponseEntity.class)
        .addStatement("$N.setId($N)",
            objectParam.name, idParam.name)
        .addStatement("$N.save($N)",
            repoField.name, objectParam.name)
        .addStatement("return $T.notFound().build()",
            ResponseEntity.class)
        .build();

    TypeSpec rest = TypeSpec
        .classBuilder(simpleName + "Resource")
        .addAnnotation(RestController.class)
        .addField(repoField)
        .addMethod(getAll)
        .addMethod(getOne)
        .addMethod(delete)
        .addMethod(create)
        .addMethod(update)
        .build();

    JavaFile restFile = JavaFile
        .builder(as.project.group, rest)
        .build();
    as.addClass(restFile);

    as.build();
  }

  private AnnotationSpec getDeleteAnnotation(String path, String simpleName) {
    return AnnotationSpec
        .builder(DeleteMapping.class)
        .addMember("name", path, lowerFirstLetter(simpleName))
        .build();
  }


  private AnnotationSpec getGetAnnotation(String path, String simpleName) {
    return AnnotationSpec
        .builder(GetMapping.class)
        .addMember("name", path, lowerFirstLetter(simpleName))
        .build();
  }

  private String lowerFirstLetter(String simpleName) {
    return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
  }
}