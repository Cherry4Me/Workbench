package de.cherry.workbench.pattern.js;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.shapesecurity.functional.data.ImmutableList;
import com.shapesecurity.functional.data.Maybe;
import com.shapesecurity.shift.ast.*;
import com.shapesecurity.shift.codegen.CodeGen;
import com.shapesecurity.shift.parser.JsError;
import com.shapesecurity.shift.parser.Parser;
import de.cherry.workbench.pattern.js.mapping.JsBinding;

import java.io.IOException;
import java.lang.Class;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Js4Workbench {

  public static void main(String[] args) throws IOException, JsError {

    String content = new String(Files.readAllBytes(Paths.get("C:\\Projekte\\untitled\\untiteldMaven\\src\\main\\resources\\some-script.js")));
    Script script = Parser.parseScript(content);

    //update ast
    BindingIdentifier hallo = new BindingIdentifier("hallo");
    LiteralStringExpression stringExpression = new LiteralStringExpression("welt");
    Maybe<Expression> expressionMaybe = Maybe.just(stringExpression);
    VariableDeclarator variableDeclarator = new VariableDeclarator(hallo, expressionMaybe);
    VariableDeclaration declaration = new VariableDeclaration(
        VariableDeclarationKind.Var, ImmutableList.from(variableDeclarator));
    VariableDeclarationStatement declarationStatement = new VariableDeclarationStatement(declaration);
    Script out = script.setStatements(
        ImmutableList.from(
            Lists.newArrayList(
                Iterators.concat(
                    ImmutableList.from(declarationStatement).iterator()
                    , script.statements.iterator()))));
    //write ast
    String code = CodeGen.codeGen(out);
    System.out.println(code);

    //inspect ast
    ImmutableList<Statement> statements = script.getStatements();
    ObjectExpression objectExpression = getBindingObject(statements);
    processTree(objectExpression);

  }

  private static void processTree(ObjectExpression objectExpression) {
    for (ObjectProperty objectProperty : objectExpression.getProperties()) {
      DataProperty dataProperty = (DataProperty) objectProperty;
      System.out.print(((StaticPropertyName) dataProperty.name).value + " - ");
      Expression expression = dataProperty.expression;
      Class<?> propertyClass = expression.getClass();
      if (propertyClass.isAssignableFrom(ObjectExpression.class)) {
        processTree((ObjectExpression) expression);
      } else if (propertyClass.isAssignableFrom(LiteralStringExpression.class)) {
        LiteralStringExpression literalStringExpression = (LiteralStringExpression) expression;
        System.out.println(literalStringExpression.value);
      } else if (propertyClass.isAssignableFrom(LiteralNumericExpression.class)) {
        LiteralNumericExpression literalNumericExpression = (LiteralNumericExpression) expression;
        System.out.println(literalNumericExpression.value);
      } else if (propertyClass.isAssignableFrom(LiteralBooleanExpression.class)) {
        LiteralBooleanExpression booleanExpression = (LiteralBooleanExpression) expression;
        System.out.println(booleanExpression);
      } else if (propertyClass.isAssignableFrom(StaticMemberExpression.class)) {
        StaticMemberExpression staticMemberExpression = (StaticMemberExpression) expression;
        System.out.println(staticMemberExpression.property);
      } else if (propertyClass.isAssignableFrom(FunctionExpression.class)) {
        FunctionExpression functionExpression = (FunctionExpression) expression;
        for (BindingBindingWithDefault parameter : functionExpression.params.getItems()) {
          String name = ((BindingIdentifier) parameter).name;
          System.out.println("." + name);
        }
      } else {
        System.out.println(propertyClass);
      }
    }
  }

  private static ObjectExpression getBindingObject(ImmutableList<Statement> statements) {
    for (Statement statement : statements) {
      if (statement.getClass().isAssignableFrom(VariableDeclarationStatement.class)) {
        VariableDeclarationStatement declarationStatement = (VariableDeclarationStatement) statement;
        VariableDeclaration declaration = declarationStatement.getDeclaration();
        ImmutableList<VariableDeclarator> declarators = declaration.getDeclarators();
        for (VariableDeclarator declarator : declarators) {
          BindingIdentifier binding = (BindingIdentifier) declarator.binding;
          if ("binding".equals(binding.name)) {
            return (ObjectExpression) declarator.getInit().just();
          }
        }
      }
    }
    throw new RuntimeException("wrong script hasn't a 'var binding = {}' in it.");
  }

  public JsBinding loadBinding(String scriptString) throws JsError {
    Script script = Parser.parseScript(scriptString);
    ImmutableList<Statement> statements = script.getStatements();
    ObjectExpression objectExpression = getBindingObject(statements);
    processTree(objectExpression);
    return null;
  }
}
