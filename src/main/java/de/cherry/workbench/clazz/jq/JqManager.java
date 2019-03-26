package de.cherry.workbench.clazz.jq;

import com.shapesecurity.shift.ast.*;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.clazz.event.EventClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.js.JsTool;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class JqManager implements ClazzManager {

  @Override
  public String getClazzName() {
    return "JQuery";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    throw new NotImplementedException();
    /*JsTool js = That.getInstance().getJs();
    if (!f.getName().endsWith("js")) return null;
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
