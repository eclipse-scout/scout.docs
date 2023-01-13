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

import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.TriState;

public final class ResponsiveSnippet {

  //tag::DisableResponsive[]
  @Order(20)
  @ClassId("98af1bc6-2d62-4132-9953-55e08492f65f")
  public class MyGroupBox extends AbstractGroupBox {

    @Override
    protected TriState getConfiguredResponsive() {
      return TriState.FALSE;
    }
  }
  //end::DisableResponsive[]

}
