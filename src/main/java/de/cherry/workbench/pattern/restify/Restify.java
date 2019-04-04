package de.cherry.workbench.pattern.restify;

import com.squareup.javapoet.*;
import de.cherry.workbench.clazz.repository.ReositoryClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.meta.java.JTool;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;

import javax.lang.model.element.Modifier;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class Restify implements PatternManager {

  That that = That.getInstance();


  @PostMapping("/restify")
  @Override
  public TypeSaveObject findPattern(@RequestBody Clazz2Edit className) {
    JTool j = that.getJ();
    ReositoryClazz reositoryClazz = (ReositoryClazz) j.getFirstClazz(className);
    CtInterface ctRepository = j.getCtInterface(reositoryClazz.getFile());
    String group = that.get().group;
    String model = reositoryClazz.model;


    CtClass classForRest = j.findClass(model);

    String simpleName = classForRest.getSimpleName();

    ClassName classForRestName = ClassName.get(classForRest.getPackage().getQualifiedName()
        , simpleName);


    ClassName repoClassName = ClassName.get(ctRepository.getPackage().getQualifiedName()
        , ctRepository.getSimpleName());

    FieldSpec repoField = FieldSpec
        .builder(repoClassName, lowerFirstLetter(ctRepository.getSimpleName()), Modifier.PRIVATE)
        .addAnnotation(Autowired.class)
        .build();

    ParameterizedTypeName typeList =
        ParameterizedTypeName.get(ClassName.get(List.class), classForRestName);


    MethodSpec getAll = MethodSpec
        .methodBuilder("retrieveAll" + simpleName)
        .addModifiers(Modifier.PUBLIC)
        .returns(typeList)
        .addAnnotation(getGetAnnotation("\"/$N\"", simpleName))
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
            , Optional.class, simpleName, idParam.name, repoField.name, idParam.name)
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
            , Optional.class, simpleName, idParam.name, repoField.name, idParam.name)
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
        .builder(group, rest)
        .build();
    j.addClass(restFile);
    that.refresh();

    j.build();


    return new TypeSaveObject(Boolean.TRUE);
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


  @Override
  public String getUrl() {
    return "/pattern/restify.html";
  }

  @Override
  public String getName() {
    return "Restify";
  }


  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return clazz.equals("Repository");
  }
}
