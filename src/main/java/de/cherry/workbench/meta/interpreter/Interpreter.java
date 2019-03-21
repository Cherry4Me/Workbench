package de.cherry.workbench.meta.interpreter;

import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interpreter {

  public static HashMap<String, String> getStruckture(Class clazz) {
    HashMap<String, String> struckture = new HashMap<>();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      struckture.put(field.getName(), field.getType().getName());
    }
    return struckture;
  }

  public static List<Executable> callabals(TypeSaveObject object) {
    Class clazz = object.type;
    if (object.isNull())
      return ClassExplorer.allStaticMethods(clazz);
    else
      return ClassExplorer.allNonStaticMethods(clazz);
  }

  public static TypeSaveObject call(Executable executable, TypeSaveObject object, Object[] args) throws Exception {
    Object out;
    Class type = null;
    executable.setAccessible(true);
    if (executable.getClass().isAssignableFrom(Method.class)) {
      Method method = (Method) executable;
      out = method.invoke(object.state, args);
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


  public static TypeSaveObject call(String executable
      , TypeSaveObject typeSaveObject
      , ArrayList<Object> parsedParams
      , Class<?>[] paramClasses) throws Exception {
    Executable executable1 = typeSaveObject.type.getMethod(executable, paramClasses);
    if (executable1 == null) {
      executable1 = typeSaveObject.type.getConstructor(paramClasses);
    }
    return call(executable1, typeSaveObject, parsedParams.toArray());
  }


  /*public static TypeSaveObject call(String executable, TypeSaveObject typeSaveObject, ArrayList<Object> parsedParams) throws Exception {
    List<ExecutableDTO> executables;
    if (typeSaveObject.isNull()) {
      executables = ClassExplorer.allStaticMethods(typeSaveObject.getType());
    } else {
      executables = ClassExplorer.allNonStaticMethods(typeSaveObject.getType());
    }
    for (ExecutableDTO aExecutable: executables){
      String name = aExecutable.getName();
      if(name.equals(executable)){
         return call(aExecutable, typeSaveObject, parsedParams);
      }
    }
    throw new RuntimeException("NO Methode");
  }*/
}
