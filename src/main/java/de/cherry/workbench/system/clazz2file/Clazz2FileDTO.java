package de.cherry.workbench.system.clazz2file;

import de.cherry.workbench.system.MasterSystem;

import java.util.ArrayList;
import java.util.List;

public class Clazz2FileDTO implements MasterSystem {
  public String base;
  public List<Clazz2FileDTO> inner = new ArrayList<>();
  public List<String> clazzes = new ArrayList<>();


  public Clazz2FileDTO(String base) {
    this.base = base;
  }
}
