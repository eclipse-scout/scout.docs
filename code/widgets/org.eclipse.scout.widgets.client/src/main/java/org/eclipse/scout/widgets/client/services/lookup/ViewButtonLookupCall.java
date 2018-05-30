/*******************************************************************************
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.ui.action.view.IViewButton;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ClientSession;

@ClassId("0f99dbb7-fcd0-48b7-b967-e6961690ab09")
public class ViewButtonLookupCall extends LocalLookupCall<IViewButton> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<IViewButton>> execCreateLookupRows() {
    return ClientSession.get().getDesktop().getViewButtons().stream().map(b -> {
      String name = b.getClass().getSimpleName();
      if (StringUtility.hasText(b.getText())) {
        name += "(" + b.getText() + ")";
      }
      return new LookupRow<>(b, name);
    }).collect(Collectors.toList());

  }

}
