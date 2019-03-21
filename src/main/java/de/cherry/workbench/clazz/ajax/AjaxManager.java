package de.cherry.workbench.clazz.ajax;

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

public class AjaxManager implements ClazzManager {

  CurrentProject project = CurrentProject.getInstance();

  @Override
  public String getClazzName() {
    return "Ajax";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    ArrayList<AjaxClazz> ajaxClazzes = new ArrayList<>();
    String name = f.getName();
    if (!name.endsWith(".js") /*&& !name.endsWith(".html")*/)
      return null;
    Script script = project.js.getScript(f);
    HashMap<Class, Consumer<Object>> listener = new HashMap<>();
    listener.put(CallExpression.class, o -> {
      CallExpression callExpression = (CallExpression) o;
      ExpressionSuper calleeSuper = callExpression.getCallee();
      if (!calleeSuper.getClass().isAssignableFrom(StaticMemberExpression.class)) return;
      StaticMemberExpression callee = (StaticMemberExpression) calleeSuper;
      if (!callee.getProperty().equals("ajax")) return;
      ExpressionSuper calledObject = callee.get_object();
      if (!calledObject.getClass().isAssignableFrom(IdentifierExpression.class)) return;
      IdentifierExpression calledRef = (IdentifierExpression) calledObject;
      if (!calledRef.getName().equals("$")) return;
      for (SpreadElementExpression argument : callExpression.getArguments()) {
        if (!argument.getClass().isAssignableFrom(ObjectExpression.class)) return;
        ObjectExpression objectExpression = (ObjectExpression) argument;
        AjaxClazz ajaxClazz = new AjaxClazz();
        for (ObjectProperty property : objectExpression.getProperties()) {
          if (property.getClass().isAssignableFrom(DataProperty.class)) {
            DataProperty dataProperty = (DataProperty) property;
            StaticPropertyName propertyName = (StaticPropertyName) dataProperty.getName();
            Expression expression = dataProperty.getExpression();
            if (propertyName.getValue().equals("type")) {
              ajaxClazz.type = project.js.getExpressionString(expression);
            }
            if (propertyName.getValue().equals("url")) {
              ajaxClazz.url = project.js.getExpressionString(expression);
            }
          }
        }
        ajaxClazz.setFile(f);
        ajaxClazzes.add(ajaxClazz);
      }
    });
    project.js.processTree(script, listener);
    return ajaxClazzes;
  }
}
