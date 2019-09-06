/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
