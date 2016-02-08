/*******************************************************************************
 * Copyright (c) 2010-2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSI CRM Software License v1.0
 * which accompanies this distribution as bsi-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.ui.html;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.scout.rt.ui.html.IUiTextContributor;

public class UiTextContributor implements IUiTextContributor {

  @Override
  public void contributeUiTextKeys(Set<String> textKeys) {
    textKeys.addAll(Arrays.asList("ExampleBeanFieldUiText"));
    textKeys.addAll(Arrays.asList("ExampleBeanFieldAppLinkText"));
  }

}
