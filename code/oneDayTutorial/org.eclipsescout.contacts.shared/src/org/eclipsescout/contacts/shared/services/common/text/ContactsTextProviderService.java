package org.eclipsescout.contacts.shared.services.common.text;

import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;

public class ContactsTextProviderService extends AbstractDynamicNlsTextProviderService {
  @Override
  protected String getDynamicNlsBaseName() {
    return "resources.texts.Texts";
  }
}
