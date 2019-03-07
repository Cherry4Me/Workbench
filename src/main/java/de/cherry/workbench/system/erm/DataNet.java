package de.cherry.workbench.system.erm;

import de.cherry.workbench.system.MasterSystem;

import java.util.ArrayList;
import java.util.List;

public class DataNet implements MasterSystem {

  public List<Edge> edges = new ArrayList<>();
  public List<Node> nodes = new ArrayList<>();
}
