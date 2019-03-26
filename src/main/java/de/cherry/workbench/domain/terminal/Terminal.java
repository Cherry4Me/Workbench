package de.cherry.workbench.domain.terminal;

import de.cherry.workbench.domain.DomainManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class Terminal implements DomainManager {
  @Override
  public String getName() {
    return "Terminal";
  }

  @Override
  public String getURL() {
    return "./terminal.html";
  }

  @PostMapping("exec")
  public Result exec(@RequestBody Command cmd, Builder resultBuider) {
    Result result = new Result();

    try {
      // run the Unix "ps -ef" command
      // using the Runtime exec method:
      Process p = Runtime.getRuntime().exec(cmd.cmd);
      result.text =  getData(resultBuider, p);
      result.error = getError(p);
    }
    catch (IOException e) {
      System.out.println("exception happened - here's what I know: ");
      e.printStackTrace();
    }
    return result;
  }

  public String getData(Builder resultBuilder, Process p) throws IOException {
    String s;
    BufferedReader stdInput = new BufferedReader(new
        InputStreamReader(p.getInputStream()));

    // read the output from the command
    //System.out.println("Here is the standard output of the command:\n");
    while ((s = stdInput.readLine()) != null) {
      resultBuilder.add(s);
    }
    return resultBuilder.getString();
  }

  public String getError(Process p) throws IOException {
    String s;
    BufferedReader stdError = new BufferedReader(new
        InputStreamReader(p.getErrorStream()));

    StringBuilder errorBuilder = new StringBuilder();
    // read any errors from the attempted command
    //System.out.println("Here is the standard error of the command (if any):\n");
    while ((s = stdError.readLine()) != null) {
      errorBuilder.append(s).append(System.lineSeparator());
    }
    return errorBuilder.toString();
  }
}
