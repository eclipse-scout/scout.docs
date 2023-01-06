/*
 * Copyright (c) 2023 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.bug.work;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.shared.Icons;

@ClassId("0d4880dc-8108-4338-b096-775eed84018d")
public class InvisibleOutline extends AbstractOutline {

  @Override
  protected String getConfiguredIconId() {
    return Icons.CircleSolid;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Inv";
  }

  @Override
  protected boolean getConfiguredOutlineOverviewVisible() {
    return false;
  }

  @Override
  protected boolean getConfiguredRootHandlesVisible() {
    return false;
  }
}
