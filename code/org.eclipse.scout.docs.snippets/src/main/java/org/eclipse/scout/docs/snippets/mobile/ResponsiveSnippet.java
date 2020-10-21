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
