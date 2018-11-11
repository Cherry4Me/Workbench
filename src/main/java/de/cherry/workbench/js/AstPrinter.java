package de.cherry.workbench.js;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class AstPrinter {
  public void print(RuleContext ctx) {
    explore(ctx, 0);
  }

  private void explore(RuleContext ctx, int indentation) {
    String ruleName = JavaScriptParser.ruleNames[ctx.getRuleIndex()];
    /*for (int j=0;j<indentation;j++) {
      System.out.print("  ");
    }*/
    //System.out.println(ruleName);
    for (int i=0;i<ctx.getChildCount();i++) {
      ParseTree element = ctx.getChild(i);
      if (element instanceof RuleContext) {
        explore((RuleContext)element, indentation + 1);
      } else {
        System.out.print(element.getText());
      }
    }
  }
}
