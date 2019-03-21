package de.cherry.workbench.explorer;

import de.cherry.workbench.meta.interpreter.Interpreter;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import org.junit.Test;

import java.lang.reflect.Executable;
import java.util.List;

public class InterpreterTest {

  @Test
  public void callabals() throws Exception {

    Interpreter interpreter = new Interpreter();
    TypeSaveObject math = new TypeSaveObject(Math.class);
    List<Executable> callabals = interpreter.callabals(math);

    Object[] ints = {2, 23};
    Class[] classes = {Integer.class, Integer.class};

    TypeSaveObject call = interpreter.call(callabals.get(16), math, ints);

    System.out.println("ende");
  }

  @Test
  public void call() {
  }

}