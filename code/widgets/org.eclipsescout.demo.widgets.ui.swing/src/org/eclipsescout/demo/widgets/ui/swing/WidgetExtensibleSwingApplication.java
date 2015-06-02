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
package org.eclipsescout.demo.widgets.ui.swing;

import java.util.List;

import org.eclipse.scout.rt.ui.swing.ExtensibleSwingApplication;
import org.eclipse.scout.rt.ui.swing.RankedExtension;
import org.eclipse.scout.rt.ui.swing.extension.ISwingApplicationExtension;

/**
 * Runs the SwingApplication with the highest priority only
 */
public class WidgetExtensibleSwingApplication extends ExtensibleSwingApplication {

  public WidgetExtensibleSwingApplication() {
    super();
  }

  @Override
  public void setExtensionsToRun(List<RankedExtension> rankedExtensions) {
    if (rankedExtensions.size() > 0) {
      ISwingApplicationExtension extensionWithHighestPriority = rankedExtensions.get(0).extension;
      m_extensions.add(extensionWithHighestPriority);
      defaultExtension = extensionWithHighestPriority;
    }
  }
}
