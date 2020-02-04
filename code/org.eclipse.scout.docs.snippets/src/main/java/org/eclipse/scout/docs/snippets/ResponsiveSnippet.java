package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.TriState;

public final class ResponsiveSnippet {

  //tag::UserAgentUtility[]
  @Order(20)
  @ClassId("98af1bc6-2d62-4132-9953-55e08492f65f")
  public class MyGroupBox extends AbstractGroupBox {

    @Override
    protected TriState getConfiguredResponsive() {
      return TriState.parse(false);
    }
  }
  //end::UserAgentUtility[]

}
