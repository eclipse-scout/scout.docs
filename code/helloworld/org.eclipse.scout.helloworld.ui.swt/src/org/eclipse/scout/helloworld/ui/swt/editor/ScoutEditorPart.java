package org.eclipse.scout.helloworld.ui.swt.editor;

import org.eclipse.scout.helloworld.ui.swt.Activator;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.window.desktop.editor.AbstractScoutEditorPart;

public class ScoutEditorPart extends AbstractScoutEditorPart {

  @Override
  protected ISwtEnvironment getSwtEnvironment() {
    return Activator.getDefault().getEnvironment();
  }
}
