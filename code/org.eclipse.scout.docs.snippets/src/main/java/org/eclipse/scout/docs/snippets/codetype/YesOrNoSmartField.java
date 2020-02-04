package org.eclipse.scout.docs.snippets.codetype;

import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;

//tag::YesOrNoSmartField[]
@ClassId("08ccc68e-7b72-4fe0-b666-245ddb8b8441")
public class YesOrNoSmartField extends AbstractSmartField<Boolean> {

  // other configuration of properties.

  @Override
  protected Class<? extends ICodeType<?, Boolean>> getConfiguredCodeType() {
    return YesOrNoCodeType.class;
  }
}
//end::YesOrNoSmartField[]
