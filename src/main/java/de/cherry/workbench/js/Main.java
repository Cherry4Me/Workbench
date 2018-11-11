package de.cherry.workbench.js;

import org.antlr.v4.runtime.RuleContext;

import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    ParserFacade parserFacade = new ParserFacade();
    AstPrinter astPrinter = new AstPrinter();
    File file = new File("./antlr_js/src/main/resources/example.js");
    JavaScriptParser.ProgramContext parse = parserFacade.parse(file);
    System.out.println("test");
    astPrinter.print(parse);
  }

  private static String name(RuleContext ctx){
    String ruleName = JavaScriptParser.ruleNames[ctx.getRuleIndex()];
    String text = ctx.getText();
    return text + " is " + ruleName;
  }
}
