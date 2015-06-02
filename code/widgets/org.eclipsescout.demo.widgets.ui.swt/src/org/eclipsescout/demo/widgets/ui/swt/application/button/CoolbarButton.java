/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.ui.swt.application.button;

import org.eclipse.jface.action.Action;
import org.eclipse.scout.rt.client.ui.action.IAction;
import org.eclipsescout.demo.widgets.ui.swt.Activator;

public class CoolbarButton extends Action {

  private IAction m_button;

  public CoolbarButton() {
    setText(" ");
    setEnabled(false);
  }

  public void init(IAction b) {
    setText(b.getText());
    setId(b.getActionId());
    setActionDefinitionId(b.getActionId());
    setToolTipText(b.getTooltipText());
    setImageDescriptor(Activator.getDefault().getEnvironment().getImageDescriptor(b.getIconId()));
    m_button = b;
  }

  @Override
  public void run() {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        m_button.setSelected(true);
        m_button.getUIFacade().fireActionFromUI();
      }
    };
    Activator.getDefault().getEnvironment().invokeScoutLater(r, 10000);
  }
}
