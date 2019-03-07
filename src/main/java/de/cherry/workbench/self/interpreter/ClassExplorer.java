package de.cherry.workbench.self.interpreter;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class ClassExplorer {

  public static List<Package> allPackages() {
    return Arrays.asList(Package.getPackages());
  }


  public static Set<Class<?>> allInPackage(Package source) {

    List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
    classLoadersList.add(ClasspathHelper.contextClassLoader());
    classLoadersList.add(ClasspathHelper.staticClassLoader());

    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(source.getName()))));

    return reflections.getSubTypesOf(Object.class);
  }

  public static List<Method> allMethods(Class clazz) {
    Method[] declaredMethods = clazz.getMethods();
    return Arrays.asList(declaredMethods);
  }

  public static List<Executable> allStaticMethods(Class clazz) {
    List<Method> methods = allMethods(clazz);
    ArrayList<Executable> statics = new ArrayList<>();
    for (Method method : methods) {
      if (Modifier.isStatic(method.getModifiers())) {
        statics.add(method);
      }
    }
    Constructor[] constructors = clazz.getConstructors();
    statics.addAll(Arrays.asList(constructors));
    return statics;
  }

  public static List<Executable> allNonStaticMethods(Class clazz) {
    List<Method> methods = allMethods(clazz);
    ArrayList<Executable> nonStatic = new ArrayList<>();
    for (Method method : methods) {
      if (!Modifier.isStatic(method.getModifiers())) {
        nonStatic.add(method);
      }
    }
    return nonStatic;
  }
}
