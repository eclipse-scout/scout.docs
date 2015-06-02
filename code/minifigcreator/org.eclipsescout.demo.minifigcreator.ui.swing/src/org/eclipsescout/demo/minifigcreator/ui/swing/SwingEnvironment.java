/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.minifigcreator.ui.swing;

import javax.swing.JComponent;

import org.eclipse.scout.commons.ITypeWithClassId;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.IBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;
import org.eclipse.scout.rt.ui.swing.basic.ISwingScoutComposite;

public class SwingEnvironment extends com.bsiag.scout.rt.ui.swing.rayo.RayoSwingEnvironment {

  @Override
  protected void assignWidgetId(ITypeWithClassId model, ISwingScoutComposite uiField) {
    if (isWidgetIdsEnabled()) {
      JComponent swingField = uiField.getSwingField();
      if (swingField != null) {
        swingField.setName(model.classId());
      }
    }
  }

  //------ Workaround for Bug 447765 ------
  @Override
  protected void decorate(Object scoutObject, Object swingScoutComposite) {
    if (scoutObject instanceof IGroupBox) {
      callAssignWidgetId(scoutObject, swingScoutComposite);
    }
    else if (scoutObject instanceof IStringField
        && !((IStringField) scoutObject).isInputMasked()
        && !((IStringField) scoutObject).isMultilineText()) {
      callAssignWidgetId(scoutObject, swingScoutComposite);
    }
    else if (scoutObject instanceof IBooleanField) {
      callAssignWidgetId(scoutObject, swingScoutComposite);
    }
    super.decorate(scoutObject, swingScoutComposite);
  }

  private void callAssignWidgetId(Object scoutObject, Object swingScoutComposite) {
    if (scoutObject instanceof ITypeWithClassId && swingScoutComposite instanceof ISwingScoutComposite) {
      ITypeWithClassId model = (ITypeWithClassId) scoutObject;
      ISwingScoutComposite uiField = (ISwingScoutComposite) swingScoutComposite;
      assignWidgetId(model, uiField);
    }
  }
  // ------ end workaround ------
}
