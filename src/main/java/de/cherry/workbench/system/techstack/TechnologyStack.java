package de.cherry.workbench.system.techstack;

import de.cherry.workbench.meta.domain.Project;

import java.io.IOException;

public interface TechnologyStack {
  void build(Project project) throws IOException;
}
