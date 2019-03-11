package de.cherry.workbench.clazz.ui;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import spoon.reflect.declaration.CtClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UiManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Ui";
  }

  @Override
  public MasterClazz readClazz(File f) {
    String html = null;
    try {
      String absolutePath = f.getAbsolutePath();
      html = new String(Files.readAllBytes(Paths.get(absolutePath)));
      Document document = Jsoup.parse(html);
      String title = document.title();
      String bodyText = document.body().html();
      return new UiClazz(title, bodyText, absolutePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public MasterClazz readClazz(CtClass aClass) {
    throw new RuntimeException("Can't create from Java-Class");
  }

  @Override
  public boolean detect(File f) {
    return f.getAbsolutePath().endsWith(".html");
  }

  @Override
  public boolean detect(CtClass aClass) {
    return false;
  }
}
