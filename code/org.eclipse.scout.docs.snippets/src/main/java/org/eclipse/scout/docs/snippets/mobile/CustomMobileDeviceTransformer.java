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
