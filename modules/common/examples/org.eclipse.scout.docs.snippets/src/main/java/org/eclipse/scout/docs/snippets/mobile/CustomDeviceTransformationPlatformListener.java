/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.docs.snippets.mobile;

import org.eclipse.scout.rt.client.transformation.DeviceTransformationPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.Replace;

//tag::ExcludeAllTransformations[]
@Replace
public class CustomDeviceTransformationPlatformListener extends DeviceTransformationPlatformListener {

  @Override
  public void stateChanged(PlatformEvent event) {
    // Do nothing to not register any extension so no transformation will happen
  }
}
//end::ExcludeAllTransformations[]
