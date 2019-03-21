package de.cherry.workbench.meta.interpreter.rest;

import de.cherry.workbench.meta.interpreter.ClassExplorer;
import de.cherry.workbench.meta.interpreter.dto.ClassDTO;
import de.cherry.workbench.meta.interpreter.dto.MethodeDTO;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;

@RestController
public class ExplorerRest {

  private ClassExplorer classExplorer = new ClassExplorer();

  @GetMapping("package")
  public List<String> getPackages() {
    ArrayList<String> strings = new ArrayList<>();
    for (Package aPackage : classExplorer.allPackages()) {
      strings.add(aPackage.getName());
    }
    return strings;
  }


  @GetMapping("class")
  public List<ClassDTO> getClasses(@RequestParam("package") String aPackage) {
    Package aPackage1 = Package.getPackage(aPackage);
    Set<Class<?>> classes = ClassExplorer.allInPackage(aPackage1);
    return ClassDTO.from(classes);
  }


  @GetMapping("methode")
  public List<MethodeDTO> getMethodes(@RequestParam("class") String qualifiedClassName) throws ClassNotFoundException {
    Class<?> aClass = Class.forName(qualifiedClassName);
    List<Method> methods = ClassExplorer.allMethods(aClass);
    return MethodeDTO.from(methods);
  }
}
