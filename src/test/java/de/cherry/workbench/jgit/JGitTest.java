package de.cherry.workbench.jgit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.io.File;

public class JGitTest {

  @Test
  public void test() throws GitAPIException {
    Git git = Git.cloneRepository()
        .setURI("https://github.com/eclipse/jgit.git")
        .setDirectory(new File("/Users/mbaaxur/Desktop/test/"))
        .call();

  }
}
