package org.eclipse.scout.docs.snippets.lookup;

import java.util.Date;

import org.eclipse.scout.docs.snippets.ILanguageLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

//tag::LanguageLookupCall[]
public class LanguageLookupCall extends LookupCall<String> implements ILookupCall<String> {
  // other stuff like serialVersionUID, Lookup Service definition...

  private static final long serialVersionUID = 1L;

  private Date m_validityFrom;
  private Date m_validityTo;

  @Override
  protected Class<? extends ILookupService<String>> getConfiguredService() {
    return ILanguageLookupService.class;
  }

  public Date getValidityFrom() {
    return m_validityFrom;
  }

  public void setValidityFrom(Date validityFrom) {
    this.m_validityFrom = validityFrom;
  }

  public Date getValidityTo() {
    return m_validityTo;
  }

  public void setValidityTo(Date validityTo) {
    this.m_validityTo = validityTo;
  }
}
//end::LanguageLookupCall[]
