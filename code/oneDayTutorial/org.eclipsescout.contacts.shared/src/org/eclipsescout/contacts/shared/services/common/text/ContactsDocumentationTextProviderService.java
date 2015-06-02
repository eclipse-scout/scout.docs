package org.eclipsescout.contacts.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsDocumentationTextProviderService;

public class ContactsDocumentationTextProviderService extends AbstractDynamicNlsDocumentationTextProviderService {

  @Override
  protected String getDynamicNlsBaseName() {
    return "resources.docs.Docs";
  }
}
