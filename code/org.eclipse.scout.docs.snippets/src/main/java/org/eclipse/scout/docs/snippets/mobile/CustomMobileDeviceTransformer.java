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

import org.eclipse.scout.rt.client.transformation.MobileDeviceTransformation;
import org.eclipse.scout.rt.client.transformation.MobileDeviceTransformer;
import org.eclipse.scout.rt.platform.Replace;

//tag::ExcludeTransformationGlobally[]
@Replace
public class CustomMobileDeviceTransformer extends MobileDeviceTransformer {

  @Override
  protected void initTransformationConfig() {
    super.initTransformationConfig();
    getDeviceTransformationConfig().disableTransformation(MobileDeviceTransformation.MOVE_FIELD_LABEL_TO_TOP);
  }
}
//end::ExcludeTransformationGlobally[]
