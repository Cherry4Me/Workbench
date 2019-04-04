package de.cherry.workbench.pattern.repositorycreator;

import com.squareup.javapoet.*;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.clazz.model.ModelClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import de.cherry.workbench.meta.java.JTool;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spoon.reflect.declaration.CtClass;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.List;

@RestController
public class RepositoryCreator implements PatternManager {

  That that = That.getInstance();


  @PostMapping("/repositoryTemplate")
  @Override
  public TypeSaveObject findPattern(@RequestBody Clazz2Edit className) {
    JTool j = that.getJ();
    ModelClazz model = (ModelClazz) j.getFirstClazz(className);
    CtClass ctClass = j.findClass(model.getFile());
    String group = that.get().group;
    ClassName longClass = ClassName.get(Long.class);
    String simpleName = ctClass.getSimpleName();
    ClassName poetClass = ClassName.get(ctClass.getPackage().getQualifiedName()
        , simpleName);
    ParameterizedTypeName jpaRepoRef =
        ParameterizedTypeName.get(ClassName.get(JpaRepository.class), poetClass, longClass);
    String repoName = simpleName + "Repository";
    TypeSpec repository = TypeSpec
        .interfaceBuilder(repoName)
        .addSuperinterface(jpaRepoRef)
        .build();
    JavaFile javaFile = JavaFile
        .builder(group, repository)
        .build();
    j.addClass(javaFile);
    return new TypeSaveObject(Boolean.TRUE);
  }


  @Override
  public String getUrl() {
    return "/pattern/repositoryCreator.html";
  }

  @Override
  public String getName() {
    return "Create Repository";
  }


  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return clazz.equals("ModelClazz");
  }
}
