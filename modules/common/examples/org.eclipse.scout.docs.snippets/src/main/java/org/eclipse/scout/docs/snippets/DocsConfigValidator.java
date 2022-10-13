package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.platform.config.IConfigurationValidator;

public class DocsConfigValidator implements IConfigurationValidator {

  @Override
  public boolean isValid(String key, String value) {
    // the config.properties contains an example entry with this key. It should be ignored in validation.
    return "myproject.applicationName".equals(key);
  }
}
