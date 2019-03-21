package de.cherry.workbench.clazz.jq;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.meta.js.JsTool;
import spoon.reflect.declaration.CtClass;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.List;

public class JqManager implements ClazzManager {
  CurrentProject project = CurrentProject.getInstance();

  @Override
  public String getClazzName() {
    return "JQuery";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    throw new NotImplementedException();
    /*if (!f.getName().endsWith("js")) return null;
    Script script = js.getScript(f);
    ArrayList<EventClazz> eventClazzes = new ArrayList<>();
    HashMap<Class, Consumer<Object>> listener = new HashMap<>();
    listener.put(CallExpression.class, o -> {
      CallExpression callExpression = (CallExpression) o;
      ExpressionSuper calleeSuper = callExpression.getCallee();
      if (!calleeSuper.getClass().isAssignableFrom(StaticMemberExpression.class)) return;
      StaticMemberExpression callee = (StaticMemberExpression) calleeSuper;
      ExpressionSuper calledObject = callee.get_object();
      if (!calledObject.getClass().isAssignableFrom(IdentifierExpression.class)) return;
      IdentifierExpression calledRef = (IdentifierExpression) calledObject;
      if (!calledRef.getName().equals("$")) return;
      System.out.println("");

    });
    js.processTree(script, listener);
    return eventClazzes;
    */
  }

}
