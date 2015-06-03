package org.eclipse.scout.helloworld.ui.swt.views;

import org.eclipse.scout.helloworld.ui.swt.Activator;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.window.desktop.view.AbstractScoutView;

public class WestView extends AbstractScoutView {

  public WestView() {
  }

  @Override
  protected ISwtEnvironment getSwtEnvironment() {
    return Activator.getDefault().getEnvironment();
  }
}
