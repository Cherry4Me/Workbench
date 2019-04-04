package de.cherry.workbench.system.pages;

import de.cherry.workbench.meta.That;
import de.cherry.workbench.system.SystemManager;
import de.cherry.workbench.system.erm.DataNet;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Pages implements SystemManager {

  That that = That.getInstance();

  @GetMapping("pages")
  public List<String> getPages() throws IOException {
    File web = that.getJs().webDir;
    return Files.walk(Paths.get(web.getAbsolutePath()))
        .filter(Files::isRegularFile)
        .filter((path -> "html".equals(FilenameUtils.getExtension(path.toString()))))
        .map(path -> path.toString().split("webapp")[1])
        .collect(Collectors.toList());
  }

  @PostMapping("createPage")
  public boolean setModel(@RequestBody Filename filename) throws FileNotFoundException {

    File file = new File(that.getJs().webDir.getAbsolutePath() + File.separator + filename.filename);
    if (file.exists()) return false;
    HTMLTemplate htmlTemplate = new HTMLTemplate(filename.filename.split("\\.")[0]);
    try (PrintWriter out = new PrintWriter(file.getAbsolutePath())) {
      out.println(htmlTemplate.build());
    }
    return true;
  }

  @Override
  public String getURL() {
    return "/system/pages.html";
  }

  @Override
  public String getName() {
    return "Pages";
  }
}
