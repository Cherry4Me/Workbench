package de.cherry.workbench.meta.js;

import com.shapesecurity.shift.ast.*;
import com.shapesecurity.shift.parser.JsError;
import com.shapesecurity.shift.parser.Parser;
import de.cherry.workbench.meta.file.Dir;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.Class;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class JsTool {

  public Dir webDir;

  public JsTool(Dir webDir) {
    this.webDir = webDir;
  }

  @NotNull
  public String getExpressionString(Expression expression) {
    Class<? extends Expression> aClass = expression.getClass();
    if (aClass.isAssignableFrom(LiteralStringExpression.class)) {
      return ((LiteralStringExpression) expression).getValue();
    } else if (aClass.isAssignableFrom(BinaryExpression.class)) {
      BinaryExpression binaryExpression = (BinaryExpression) expression;
      return getExpressionString(binaryExpression.getLeft())
          + " "
          + binaryExpression.getOperator().getName()
          + " "
          + getExpressionString(binaryExpression.getRight());
    } else if (aClass.isAssignableFrom(IdentifierExpression.class)) {
      IdentifierExpression identifierExpression = (IdentifierExpression) expression;
      return identifierExpression.getName();
    }
    throw new RuntimeException("Can't print: " + aClass.getName());
  }

  public void processTree(Object someThing, HashMap<Class, Consumer<Object>> events) {
    Class<?> thingClass = someThing.getClass();
    HashMap<Class, Consumer<Object>> map = new HashMap<>();
    map.put(Script.class, o -> {
      Script script = (Script) o;
      for (Statement statement : script.getStatements()) {
        processTree(statement, events);
      }
    });
    map.put(ExpressionStatement.class, o -> {
      ExpressionStatement expressionStatement = (ExpressionStatement) o;
      processTree(expressionStatement.getExpression(), events);
    });
    map.put(CallExpression.class, o -> {
      CallExpression callExpression = (CallExpression) o;
      processTree(callExpression.getCallee(), events);
      for (SpreadElementExpression argument : callExpression.getArguments()) {
        processTree(argument, events);
      }
    });
    map.put(FunctionExpression.class, o -> {
      FunctionExpression functionExpression = (FunctionExpression) o;
      processTree(functionExpression.getBody(), events);
      for (BindingBindingWithDefault item : functionExpression.getParams().getItems()) {
        processTree(item, events);
      }
    });
    map.put(StaticMemberExpression.class, o -> {
      StaticMemberExpression staticMemberExpression = (StaticMemberExpression) o;
      processTree(staticMemberExpression.get_object(), events);
    });
    map.put(IdentifierExpression.class, o -> {
      IdentifierExpression identifierExpression = (IdentifierExpression) o;
    });
    map.put(FunctionBody.class, o -> {
      FunctionBody functionBody = (FunctionBody) o;
      for (Statement statement : functionBody.getStatements()) {
        processTree(statement, events);
      }
      for (Directive directive : functionBody.getDirectives()) {
        processTree(directive, events);
      }
    });
    map.put(ObjectExpression.class, o -> {
      ObjectExpression objectExpression = (ObjectExpression) o;
      for (ObjectProperty property : objectExpression.getProperties()) {
        processTree(property, events);
      }
    });
    map.put(DataProperty.class, o -> {
      DataProperty dataProperty = (DataProperty) o;
      processTree(dataProperty.getExpression(), events);
    });
    map.put(LiteralStringExpression.class, o -> {
      LiteralStringExpression literalStringExpression = (LiteralStringExpression) o;
    });
    map.put(BindingIdentifier.class, o -> {
      BindingIdentifier bindingIdentifier = (BindingIdentifier) o;
    });
    Consumer<Object> consumer = null;
    for (Class key : map.keySet()) {
      if (thingClass.isAssignableFrom(key))
        consumer = map.get(key);
    }
    if (consumer == null)
      System.err.println(thingClass.getName());
    else {
      consumer.accept(someThing);
    }
    Consumer<Object> event = events.get(thingClass);
    if (event != null)
      event.accept(someThing);
  }

  @NotNull
  public Script getScript(File f) {
    try {
      String content = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())));
      return Parser.parseScript(content);
    } catch (IOException | JsError e) {
      throw new RuntimeException(e);
    }
  }

  public String getCalled(ExpressionSuper calledObject) {
    if (calledObject.getClass().isAssignableFrom(CallExpression.class)) {
      CallExpression callExpression = (CallExpression) calledObject;
      ExpressionSuper callee = callExpression.getCallee();
      if (callee.getClass().isAssignableFrom(IdentifierExpression.class)) {
        String calleeName = ((IdentifierExpression) callee).getName();
        if (calleeName.equals("$")) {
          for (SpreadElementExpression argument : callExpression.getArguments()) {
            return "$(" + getExpressionString((Expression) argument) + ")";
          }
        }
      }
    }
    throw new IllegalStateException("Can't find called Exeption");
  }
}
