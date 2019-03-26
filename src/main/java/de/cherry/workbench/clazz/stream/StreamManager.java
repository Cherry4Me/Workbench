package de.cherry.workbench.clazz.stream;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.java.JTool;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.support.reflect.code.CtInvocationImpl;
import spoon.support.reflect.code.CtLocalVariableImpl;
import spoon.support.reflect.code.CtReturnImpl;
import spoon.support.reflect.code.CtVariableReadImpl;
import spoon.support.reflect.declaration.CtMethodImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StreamManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Stream";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = That.getInstance().getJ();
    CtClass aClass = j.getCtClass(f);
    if (aClass == null)
      return null;
    StreamClazz streamClazz = new StreamClazz();
    for (Object o : aClass.getMethods()) {
      CtMethodImpl method = (CtMethodImpl) o;
      for (CtStatement statement : method.getBody().getStatements()) {
        SourcePosition position = statement.getPosition();
        for (CtElement ctElement : statement.asIterable()) {
          Iterable<CtElement> ctElements = Arrays.asList();
          if (ctElement.getClass().isAssignableFrom(CtLocalVariableImpl.class)) {
            ctElements = ((CtLocalVariableImpl) ctElement).getDefaultExpression().asIterable();
          }
          if (ctElement.getClass().isAssignableFrom(CtReturnImpl.class)) {
            ctElements = ((CtReturnImpl) ctElement).getReturnedExpression().asIterable();
          }
          List<String> calls = createStreamCalls(ctElements);
          if (calls != null)
            streamClazz.calls.put(position, calls);

        }
      }
    }
    if (streamClazz.calls.isEmpty()) return null;
    return Arrays.asList(streamClazz);
  }

  private List<String> createStreamCalls(Iterable<CtElement> ctElements) {
    Iterator<CtElement> iterator = ctElements.iterator();
    List<CtElement> list = new ArrayList<>();
    iterator.forEachRemaining(list::add);
    boolean inStream = false;
    ArrayList<String> calls = new ArrayList<>();
    for (int i = list.size() - 1; i >= 0; i--) {
      CtElement ctElement = list.get(i);
      String streamClassPath = "java.util.stream.Stream";
      if (ctElement.getClass().isAssignableFrom(CtVariableReadImpl.class)) {
        CtVariableReadImpl variable = (CtVariableReadImpl) ctElement;
        if (variable.getType().getQualifiedName().equals(streamClassPath)) {
          inStream = true;
        }
      }
      if (ctElement.getClass().isAssignableFrom(CtInvocationImpl.class)) {
        CtInvocationImpl invocation = (CtInvocationImpl) ctElement;
        if (inStream) {
          calls.add(invocation.getExecutable().getSimpleName());
        }
        if (invocation.getType().getQualifiedName().equals(streamClassPath)) {
          inStream = true;
        }
      }
    }
    if (inStream)
      return calls;
    else
      return null;
  }
}
