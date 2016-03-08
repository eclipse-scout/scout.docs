package org.eclipse.scout.docs.snippets.platform.config;

import org.eclipse.scout.rt.platform.IgnoreBean;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;

@IgnoreBean
@Replace
public class ApplicationNameConstant extends ApplicationNameProperty {
  @Override
  protected String createValue() {
    return "Contacts Application";
  }
}
