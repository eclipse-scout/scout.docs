package org.eclipse.scout.contacts.shared.services.common.text;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.rt.shared.services.common.text.AbstractDynamicNlsTextProviderService;

@Order(-2000)
public class DefaultTextProviderService extends AbstractDynamicNlsTextProviderService {
  @Override
  protected String getDynamicNlsBaseName() {
    return "org.eclipse.scout.contacts.shared.texts.Texts";
  }
}
