package de.cherry.workbench.js;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class ParserFacade {
  private static String readFile(File file, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(file.toPath());
    return new String(encoded, encoding);
  }

  public JavaScriptParser.ProgramContext parse(File file) throws IOException {
    String code = readFile(file, Charset.forName("UTF-8"));
    JavaScriptLexer lexer = new JavaScriptLexer(new ANTLRInputStream(code));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    JavaScriptParser parser = new JavaScriptParser(tokens);
    return parser.program();
  }
}
