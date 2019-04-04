package de.cherry.workbench.clazz.repository;

import de.cherry.workbench.clazz.ClazzManager;
import de.cherry.workbench.clazz.MasterClazz;
import de.cherry.workbench.meta.That;
import de.cherry.workbench.meta.java.JTool;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.reference.CtTypeReference;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RepositoryManager implements ClazzManager {

  @Override
  public String getClazzName() {
    return "Repository";
  }

  @Override
  public List<? extends MasterClazz> readClazz(File f) {
    JTool j = That.getInstance().getJ();
    CtInterface ctInterface = j.getCtInterface(f);
    if (ctInterface == null) return null;
    for (CtTypeReference<?> superInterface : ctInterface.getSuperInterfaces()) {
      if ("JpaRepository".equals(superInterface.getSimpleName())) {
        CtTypeReference<?> ctTypeReference = superInterface.getActualTypeArguments().get(0);
        ReositoryClazz reositoryClazz = new ReositoryClazz();
        reositoryClazz.setFile(f);
        reositoryClazz.model = ctTypeReference.getQualifiedName();
        return Collections.singletonList(reositoryClazz);
      }
    }
    return null;
  }
}
