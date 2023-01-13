/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.mail.smtp.SmtpServerConfig;
import org.eclipse.scout.rt.platform.BEANS;

public final class SmtpServerConfigSnippet {

  void snippets() throws Exception {
    // tag::SmtpServerConfig.example[]
    @SuppressWarnings("unused")
    SmtpServerConfig smtpServerConfig = BEANS.get(SmtpServerConfig.class)
        .withHost("mail.example.com")
        .withPort(465)
        .withUsername("smtpuser")
        .withPassword("smtpuserpwd")
        .withUseAuthentication(true)
        .withUseSmtps(true)
        .withUseStartTls(true);
    // end::SmtpServerConfig.example[]
  }
}
