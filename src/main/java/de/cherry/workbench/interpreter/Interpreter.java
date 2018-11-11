package de.cherry.workbench.interpreter;

import de.cherry.workbench.explorer.ClassExplorer;
import de.cherry.workbench.explorer.TypeSaveObject;
import sun.tools.java.ClassType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter {

  public static List<Executable> callabals(TypeSaveObject object) {
    Class clazz = object.getType();
    if (object.isNull())
      return ClassExplorer.allStaticMethods(clazz);
    else
      return ClassExplorer.allNonStaticMethods(clazz);
  }

  public static TypeSaveObject call(Executable executable, TypeSaveObject object, Object... args) throws Exception {
    Object out;
    Class type = null;
    executable.setAccessible(true);
    if (executable.getClass().isAssignableFrom(Method.class)) {
      Method method = (Method) executable;
      out = method.invoke(object.getState(), args);
      if (out == null) {
        type = method.getReturnType();
      }
    } else if (executable.getClass().isAssignableFrom(Constructor.class)) {
      Constructor constructor = (Constructor) executable;
      out = constructor.newInstance(args);
    } else
      throw new RuntimeException();
    if (type != null) {
      return new TypeSaveObject(type);
    }

    if (out != null) {
      return new TypeSaveObject(out);
    }
    throw new RuntimeException();

  }


  public static TypeSaveObject call(String executable, TypeSaveObject typeSaveObject, ArrayList<Object> parsedParams) throws Exception {
    List<Executable> executables;
    if (typeSaveObject.isNull()) {
      executables = ClassExplorer.allStaticMethods(typeSaveObject.getType());
    } else {
      executables = ClassExplorer.allNonStaticMethods(typeSaveObject.getType());
    }
    for (Executable aExecutable: executables){
      String name = aExecutable.getName();
      if(name.equals(executable)){
         return call(aExecutable, typeSaveObject, parsedParams);
      }
    }
    throw new RuntimeException("NO Methode");
  }
}
