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
import java.util.Arrays;
import java.util.List;

public class UiManager implements ClazzManager {
  @Override
  public String getClazzName() {
    return "Ui";
  }

  @Override
  public List<MasterClazz> readClazz(File f) {
    if(!f.getAbsolutePath().endsWith(".html")) return null;
    String html = null;
    try {
      String absolutePath = f.getAbsolutePath();
      html = new String(Files.readAllBytes(Paths.get(absolutePath)));
      Document document = Jsoup.parse(html);
      String title = document.title();
      String bodyText = document.body().html();
      return Arrays.asList(new UiClazz(title, bodyText, absolutePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
