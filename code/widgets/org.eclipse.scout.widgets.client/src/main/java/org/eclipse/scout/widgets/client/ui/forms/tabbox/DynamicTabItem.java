package org.eclipse.scout.widgets.client.ui.forms.tabbox;

import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;

/**
 * <h3>{@link DynamicTabItem}</h3>
 *
 * @author aho
 */
public class DynamicTabItem extends AbstractGroupBox {

  @Override
  protected String getConfiguredLabel() {
    return "Dynamic tab";
  }

  public class SampleField extends AbstractStringField {
    @Override
    protected String getConfiguredLabel() {
      return "Sample";
    }
  }
}
