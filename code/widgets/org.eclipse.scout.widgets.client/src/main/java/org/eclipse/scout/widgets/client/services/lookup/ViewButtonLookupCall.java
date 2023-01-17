/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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
