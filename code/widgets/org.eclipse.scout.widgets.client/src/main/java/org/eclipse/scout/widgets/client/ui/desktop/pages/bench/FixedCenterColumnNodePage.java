/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.widgets.client.ui.forms.FormForm;

@ClassId("fa1b1d26-4e3e-468b-b091-dd24671a1948")
public class FixedCenterColumnNodePage extends AbstractBenchLayoutPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Fixed center column";
  }

  @Override
  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return new BenchLayoutData()
        .withWest(new BenchColumnData().withInitial(1).withRelative(true))
        .withCenter(new BenchColumnData().withInitial(1).withRelative(true).withGrow(0).withShrink(0))
        .withEast(new BenchColumnData().withInitial(1).withRelative(true));
  }

  @Override
  protected void execCreatePageForms(List<IForm> formList) {

    // west form
    FormForm w1 = new FormForm();
    w1.setTitle("W 1");
    w1.setDisplayViewId(IForm.VIEW_ID_W);
    w1.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
    w1.setDisplayParent(getOutline());
    w1.startPageForm();
    formList.add(w1);
    // center form
    FormForm c1 = new FormForm();
    c1.setTitle("C 1");
    c1.setDisplayViewId(IForm.VIEW_ID_CENTER);
    c1.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
    c1.setDisplayParent(getOutline());
    c1.startPageForm();
    formList.add(c1);
    // east
    FormForm e1 = new FormForm();
    e1.setTitle("E 1");
    e1.setDisplayViewId(IForm.VIEW_ID_E);
    e1.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
    e1.setDisplayParent(getOutline());
    e1.startPageForm();
    formList.add(e1);

  }

}
