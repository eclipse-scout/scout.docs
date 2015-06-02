package org.eclipse.scout.helloworld.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;

public class HelloworldTextProviderService extends AbstractDynamicNlsTextProviderService {
  @Override
  protected String getDynamicNlsBaseName() {
    return "resources.texts.Texts";
  }
}
