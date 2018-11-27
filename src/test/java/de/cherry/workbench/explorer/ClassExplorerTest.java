package de.cherry.workbench.explorer;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ClassExplorerTest {

  @Test
  public void getAll() {

    List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
    classLoadersList.add(ClasspathHelper.contextClassLoader());
    classLoadersList.add(ClasspathHelper.staticClassLoader());

    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
        .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
        .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("javaDir.lang"))));

    Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
    System.out.println("test");
  }

  @Test
  public void allPackages() {
    ClassExplorer classExplorer = new ClassExplorer();
    List<Package> packages = classExplorer.allPackages();

    System.out.println("haha");
  }


  @Test
  public void allInPackages() {
  }

  @Test
  public void allMethods() {
  }

  @Test
  public void allStaticMethods() {
  }

  @Test
  public void allNonStaticMethods() {
  }
}