/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.ui.html.json;

import org.eclipse.scout.rt.ui.html.IUiSession;
import org.eclipse.scout.rt.ui.html.json.IJsonAdapter;
import org.eclipse.scout.rt.ui.html.json.JsonProperty;
import org.eclipse.scout.rt.ui.html.json.desktop.JsonOutline;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IWidgetsOutline;

public class JsonWidgetsOutline extends JsonOutline<IWidgetsOutline> {

  public JsonWidgetsOutline(IWidgetsOutline outline, IUiSession uiSession, String id, IJsonAdapter parent) {
    super(outline, uiSession, id, parent);
  }

  @Override
  public String getObjectType() {
    return "widgets.WidgetsOutline";
  }

  @Override
  protected void initJsonProperties(IWidgetsOutline model) {
    super.initJsonProperties(model);
    putJsonProperty(new JsonProperty<IWidgetsOutline>(IWidgetsOutline.PROP_DESCRIPTION, model) {
      @Override
      protected String modelValue() {
        return getModel().getDescription();
      }
    });
  }

}
