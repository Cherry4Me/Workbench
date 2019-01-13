package de.cherry.workbench.stream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Duck {

  public static <T> T typing(Object aDuck, Class<T> ducktyp) {
    return (T) Proxy.newProxyInstance(
        ducktyp.getClassLoader(),
        new Class[]{ducktyp},
        new InvocationHandler() {
          public Object invoke(
              Object proxy, Method method, Object[] args)
              throws Throwable {
            return aDuck.getClass().getMethod(
                method.getName(), method.getParameterTypes()).invoke(aDuck, args);
          }
        });

  }

}
