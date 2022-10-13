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
