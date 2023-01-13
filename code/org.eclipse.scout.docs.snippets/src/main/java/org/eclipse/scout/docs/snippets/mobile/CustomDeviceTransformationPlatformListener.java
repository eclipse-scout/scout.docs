/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
