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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.ui.action.view.IViewButton;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("ea322a27-b49b-40f0-a1d7-c90b3e02f0dd")
public class ViewButtonDisplayStyleLookupCall extends LocalLookupCall<IViewButton.DisplayStyle> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<IViewButton.DisplayStyle>> execCreateLookupRows() {
    return Arrays.asList(IViewButton.DisplayStyle.values()).stream().sorted().map(s -> {
      return new LookupRow<IViewButton.DisplayStyle>(s, s.toString());
    }).collect(Collectors.toList());
  }

}
