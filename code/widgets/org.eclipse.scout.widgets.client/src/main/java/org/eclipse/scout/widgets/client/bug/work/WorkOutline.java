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
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.widgets.client.bug.helloworld.InitForm;

/**
 * <h3>{@link WorkOutline}</h3>
 *
 * @author Florian
 */
@Order(1000)
@ClassId("94463c65-85af-4872-8afb-2feb340a407c")
public class WorkOutline extends AbstractOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Work");
  }

  @Override
  protected Class<? extends IForm> getConfiguredDefaultDetailForm() {
    return InitForm.class;
  }

  @Override
  protected boolean getConfiguredRootHandlesVisible() {
    return false;
  }
}
