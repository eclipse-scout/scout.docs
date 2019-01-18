package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.server.commons.servlet.ContentSecurityPolicy;

@Replace
public class WidgetsContentSecurityPolicy extends ContentSecurityPolicy {

  @Override
  protected void initDirectives() {
    super.initDirectives();
    // Demo app uses external images in html field and custom widgets -> allow it
    this
        .withImgSrc("'self' blob: *")
        .withFontSrc("'self' https://cdnjs.cloudflare.com")
        .withScriptSrc("'self' https://use.fontawesome.com")
        .withStyleSrc("'self' 'unsafe-inline' https://use.fontawesome.com https://cdnjs.cloudflare.com");
  }
}
