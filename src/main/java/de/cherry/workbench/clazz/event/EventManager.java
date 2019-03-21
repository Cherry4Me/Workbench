package de.cherry.workbench.clazz.event;

import com.shapesecurity.shift.ast.*;
import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.meta.js.JsTool;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.lang.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EventManager implements ClazzManager {

  CurrentProject project = CurrentProject.getInstance();

  @Override
  public String getClazzName() {
    return "Event";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    if(!f.getName().endsWith("js")) return null;
    Script script = project.js.getScript(f);
    ArrayList<EventClazz> eventClazzes = new ArrayList<>();
    HashMap<Class, Consumer<Object>> listener = new HashMap<>();
    listener.put(CallExpression.class, o -> {
      CallExpression callExpression = (CallExpression) o;
      ExpressionSuper calleeSuper = callExpression.getCallee();
      if (!calleeSuper.getClass().isAssignableFrom(StaticMemberExpression.class)) return;
      StaticMemberExpression callee = (StaticMemberExpression) calleeSuper;
      if (!callee.getProperty().equals("on")) return;
      ExpressionSuper calledObject = callee.get_object();
      String target = project.js.getCalled(calledObject);
      for (SpreadElementExpression argument : callExpression.getArguments()) {
        String event = project.js.getExpressionString((Expression) argument);
        eventClazzes.add(new EventClazz(target, event, f));
        return;
      }
    });
    project.js.processTree(script, listener);
    return eventClazzes;
  }
}
