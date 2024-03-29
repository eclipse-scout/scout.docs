/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.lookup;

import java.util.Date;

import org.eclipse.scout.docs.snippets.ILanguageLookupService;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

//tag::LanguageLookupCall[]
@ClassId("6154090e-86ac-4c08-9769-bf3ef61c1b4b")
public class LanguageLookupCall extends LookupCall<String> {
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
