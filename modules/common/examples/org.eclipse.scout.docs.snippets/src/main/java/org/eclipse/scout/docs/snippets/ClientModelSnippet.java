package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.client.ui.action.IAction;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.dimension.IDimensions;

/**
 * Snippets for Documentation
 */
public class ClientModelSnippet {

  void dimensionSnippet() {
    IFormField formField = new AbstractStringField() {
    };
    IFormField formField2 = new AbstractStringField() {
    };
    IFormField formField3 = new AbstractStringField() {
    };
    IAction menu = new AbstractMenu() {
    };
    // tag::DimensionSamples[]
    menu.setEnabled(false); // <1>
    menu.setEnabledGranted(false); // <2>
    menu.setVisible(false, IDimensions.VISIBLE_CUSTOM); // <3>
    formField.setVisible(true, false, true, "MyCustomDimension"); // <4>
    formField2.setVisible(true, true, true); // <5>

    formField3.isEnabled(IDimensions.ENABLED_CUSTOM); // <6>
    formField3.isEnabled(IDimensions.ENABLED); // <7>
    formField3.isEnabled(); // <8>
    formField3.isEnabledIncludingParents(); // <9>
    // end::DimensionSamples[]
  }

}
