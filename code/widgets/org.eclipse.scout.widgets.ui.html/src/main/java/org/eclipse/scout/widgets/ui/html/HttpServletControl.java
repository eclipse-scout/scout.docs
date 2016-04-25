package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.platform.Replace;

@Replace
public class HttpServletControl extends org.eclipse.scout.rt.server.commons.servlet.HttpServletControl {

  @Override
  protected String cspRule() {
    // Demo app uses external images in html field and custom widgets -> allow it
    return DEFAULT_CSP_RULE + "; img-src *";
  }
}
