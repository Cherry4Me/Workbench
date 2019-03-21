package de.cherry.workbench.pattern.desinger;

import de.cherry.workbench.meta.CurrentProject;
import de.cherry.workbench.clazz.ui.UiManager;
import de.cherry.workbench.pattern.PatternManager;
import de.cherry.workbench.pattern.clazzeditor.Clazz2Edit;
import de.cherry.workbench.meta.file.Dir;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DesignerManager implements PatternManager {

  CurrentProject project = CurrentProject.getInstance();


  @GetMapping("pages")
  public List<String> getPages() throws IOException {
    Dir web = project.js.webDir;
    List<String> pathList = Files.walk(Paths.get(web.getFile().getAbsolutePath()))
        .filter(Files::isRegularFile)
        .filter((path -> "html".equals(FilenameUtils.getExtension(path.toString()))))
        .map(path -> path.toString().split("webapp")[1])
        .collect(Collectors.toList());
    return pathList;
  }

  @GetMapping("page")
  public String getPage(@RequestParam("page") String page) throws IOException {
    Dir web = project.js.webDir;
    String pageFile = web.getFile().toString() + page;
    String html = new String(Files.readAllBytes(Paths.get(pageFile)));
    Document document = Jsoup.parse(html);
    document.title();
    return document.body().html();
  }

  @PostMapping("page")
  public void savePage(@RequestParam("page") String pageUrl
      , @RequestBody Page page) throws IOException {
    Dir web = project.js.webDir;
    String pageFile = web.getFile().toString() + pageUrl;
    Path path = Paths.get(pageFile);
    String html = new String(Files.readAllBytes(path));
    Document document = Jsoup.parse(html);
    document.body().html(page.getBody());
    Files.write(path, document.html().getBytes());
  }

  @Override
  public String getUrl() {
    return "pages.html";
  }

  @Override
  public String getName() {
    return "Designer";
  }

  @Override
  public TypeSaveObject findPattern(Clazz2Edit className) {
    return null;
  }

  @Override
  public void writePattern() {

  }

  @Override
  public boolean isPatternStartableForClazz(String clazz) {
    return clazz.equals(new UiManager().getClazzName());
  }
}
