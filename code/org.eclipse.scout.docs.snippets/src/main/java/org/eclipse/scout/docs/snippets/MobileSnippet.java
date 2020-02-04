package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

public final class MobileSnippet {

  //tag::UserAgentUtility[]
  @Order(20)
  @ClassId("032f5ffb-bb1a-477a-95c8-f185e930a977")
  public class MyField extends AbstractStringField {

    @Override
    protected void execInitField() {
      if (UserAgentUtility.isMobileDevice()) {
        setVisibleGranted(false);
      }
    }
  }
  //end::UserAgentUtility[]

}
