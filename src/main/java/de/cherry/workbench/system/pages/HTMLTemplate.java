package de.cherry.workbench.system.pages;

import de.cherry.workbench.meta.file.Fileable;

public class HTMLTemplate implements Fileable {

  private String name;

  public HTMLTemplate(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String build() {
    return "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "\n" +
        "<head>\n" +
        "  <meta charset=\"UTF-8\">\n" +
        "  <title>" + name + "</title>\n" +
        "  <meta content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" name=\"viewport\">\n" +
        "  <link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\n" +
        "  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\n" +
        "</head>\n" +
        "\n" +
        "<body>\n" +
        "\n" +
        "  \n" +
        "</body>\n" +
        "\n" +
        "</html>";
  }
}
