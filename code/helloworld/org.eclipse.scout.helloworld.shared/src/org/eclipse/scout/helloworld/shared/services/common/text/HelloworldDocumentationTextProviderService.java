package org.eclipse.scout.helloworld.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsDocumentationTextProviderService;

public class HelloworldDocumentationTextProviderService extends AbstractDynamicNlsDocumentationTextProviderService {

  @Override
  protected String getDynamicNlsBaseName() {
    return "resources.docs.Docs";
  }
}
