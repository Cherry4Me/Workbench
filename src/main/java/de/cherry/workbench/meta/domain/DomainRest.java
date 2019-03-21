package de.cherry.workbench.meta.domain;

import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.system.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DomainRest {

  private That that = That.getInstance();

  @GetMapping("/getDomains")
  public List<Link> getSystems() {
    return that.domainManagers
        .stream()
        .map(Link::from)
        .collect(Collectors.toList());
  }
}
