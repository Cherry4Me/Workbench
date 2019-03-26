package de.cherry.workbench.domain.terminal;

import org.junit.Test;

public class TerminalTest {

  @Test
  public void exec() {
    Terminal terminal = new Terminal();
    Command command = new Command();
    command.cmd = "ps -ef";
    Result exec = terminal.exec(command, new TableBuilder());
    System.out.println(exec.text);
  }

  @Test
  public void execText() {
    Terminal terminal = new Terminal();
    Command command = new Command();
    command.cmd = "man ls";
    Result exec = terminal.exec(command, new TextBuilder());
    System.out.println(exec.text);
  }
}