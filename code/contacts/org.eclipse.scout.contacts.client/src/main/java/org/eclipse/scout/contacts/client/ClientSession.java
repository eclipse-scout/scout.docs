/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client;

import java.util.Locale;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.ClientUIPreferences;

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
    String localeString = ClientUIPreferences.getClientPreferences(get()).get(PREF_USER_LOCALE, null);
    if (localeString != null) {
      setLocale(Locale.forLanguageTag(localeString));
    }

    setDesktop(new Desktop());
  }
}
