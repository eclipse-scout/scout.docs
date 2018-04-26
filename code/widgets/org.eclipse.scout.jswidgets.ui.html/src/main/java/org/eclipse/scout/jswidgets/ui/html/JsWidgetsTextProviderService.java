package org.eclipse.scout.jswidgets.ui.html;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.AbstractDynamicNlsTextProviderService;

@Order(3550)
public class JsWidgetsTextProviderService extends AbstractDynamicNlsTextProviderService {

  @Override
  public String getDynamicNlsBaseName() {
    return "org.eclipse.scout.jswidgets.ui.html.texts.Texts";
  }

}
