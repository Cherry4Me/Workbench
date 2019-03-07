package de.cherry.workbench.system.desinger;

import de.cherry.workbench.self.TempProject;
import de.cherry.workbench.self.file.Dir;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.js.mapping.JsBinding;
import de.cherry.workbench.system.js.mapping.JsMapping;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DesignerManager implements SystemManager {

  TempProject project = TempProject.getInstance();


  @GetMapping("pages")
  public List<String> getPages() throws IOException {
    Dir web = project.as.webDir.get();
    List<String> pathList = Files.walk(Paths.get(web.getFile().getAbsolutePath()))
        .filter(Files::isRegularFile)
        .filter((path -> "html".equals(FilenameUtils.getExtension(path.toString()))))
        .map(path -> path.toString().split("webapp")[1])
        .collect(Collectors.toList());
    return pathList;
  }

  @GetMapping("mapping")
  public JsBinding getMapping(@RequestParam("page") String page) throws Exception {
    File web = project.as.webDir.get().getFile();
    String pageFile = web.toString() + page;
    String html = new String(Files.readAllBytes(Paths.get(pageFile)));
    Document document = Jsoup.parse(html);
    JsMapping jsMapping = new JsMapping();
    JsMapping.getMapping(document.childNodes(), jsMapping);
    return JsBinding.create(jsMapping, web);
  }


  @GetMapping("page")
  public String getPage(@RequestParam("page") String page) throws IOException {
    Dir web = project.as.webDir.get();
    String pageFile = web.getFile().toString() + page;
    String html = new String(Files.readAllBytes(Paths.get(pageFile)));
    Document document = Jsoup.parse(html);
    document.title();
    return document.body().html();
  }

  @PostMapping("page")
  public void savePage(@RequestParam("page") String pageUrl
      , @RequestBody Page page) throws IOException {
    Dir web = project.as.webDir.get();
    String pageFile = web.getFile().toString() + pageUrl;
    Path path = Paths.get(pageFile);
    String html = new String(Files.readAllBytes(path));
    Document document = Jsoup.parse(html);
    document.body().html(page.getBody());
    Files.write(path, document.html().getBytes());
  }

  @Override
  public String getURL() {
    return "pages.html";
  }

  @Override
  public String getName() {
    return "Designer";
  }
}