/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.client;

import java.util.Locale;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.ClientUIPreferences;
import org.eclipse.scout.rt.platform.nls.LocaleUtility;

public class ClientSession extends AbstractClientSession {

  public static final String PREF_USER_LOCALE = "PREF_USER_LOCALE";

  public ClientSession() {
    super(true);
  }

  /**
   * @return The {@link IClientSession} which is associated with the current thread, or <code>null</code> if not found.
   */
  public static ClientSession get() {
    return ClientSessionProvider.currentSession(ClientSession.class);
  }

  @Override
  protected void execLoadSession() {
    initializeSharedVariables();

    // The locale needs to be set before the Desktop is created.
    String localeString = ClientUIPreferences.getClientPreferences(ClientSession.get()).get(PREF_USER_LOCALE, null);
    if (localeString != null) {
      Locale userLocale = LocaleUtility.parse(localeString);
      setLocale(userLocale);
    }

    setDesktop(new Desktop());
  }
}
