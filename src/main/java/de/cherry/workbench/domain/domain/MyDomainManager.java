package de.cherry.workbench.domain.domain;

import de.cherry.workbench.domain.DomainManager;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.interpreter.dto.TypeSaveObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyDomainManager implements DomainManager {



  @Override
  public String getName() {
    return "My Domain";
  }

  @Override
  public String getURL() {
    return "/domain/myDomain.html";
  }

  @PostMapping("/mydomain")
  public TypeSaveObject getMyDomain() {
    return new TypeSaveObject(That.getInstance().getMyDomain());
  }
}
