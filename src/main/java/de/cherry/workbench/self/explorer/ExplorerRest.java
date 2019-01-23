package de.cherry.workbench.self.explorer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ExplorerRest {

  private ClassExplorer classExplorer = new ClassExplorer();

  @GetMapping("/package")
  public List<String> getPackages() {
    ArrayList<String> strings = new ArrayList<>();
    for (Package aPackage : classExplorer.allPackages()) {
      strings.add(aPackage.getName());
    }
    return strings;
  }


  @GetMapping("class")
  public List<ClassWO> getClasses(@RequestParam("package") String aPackage) {
    Package aPackage1 = Package.getPackage(aPackage);
    Set<Class<?>> classes = ClassExplorer.allInPackage(aPackage1);
    return ClassWO.from(classes);
  }


  @GetMapping("methode")
  public List<MethodeWO> getMethodes(@RequestParam("class") String qualifiedClassName) throws ClassNotFoundException {
    Class<?> aClass = Class.forName(qualifiedClassName);
    List<Method> methods = ClassExplorer.allMethods(aClass);
    return MethodeWO.from(methods);
  }
}
