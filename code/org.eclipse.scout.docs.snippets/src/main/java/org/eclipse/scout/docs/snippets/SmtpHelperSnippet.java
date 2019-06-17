package org.eclipse.scout.docs.snippets;

import javax.mail.Session;

import org.eclipse.scout.rt.mail.CharsetSafeMimeMessage;
import org.eclipse.scout.rt.mail.MailAttachment;
import org.eclipse.scout.rt.mail.MailHelper;
import org.eclipse.scout.rt.mail.MailMessage;
import org.eclipse.scout.rt.mail.MailParticipant;
import org.eclipse.scout.rt.mail.smtp.SmtpHelper;
import org.eclipse.scout.rt.mail.smtp.SmtpServerConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.resource.BinaryResources;

public final class SmtpHelperSnippet {

  void snippets() throws Exception {

    byte[] bytes = new byte[0];
    // tag::SmtpHelper.prepareMimeMessage[]
    // create BinaryResource for an attachment.
    BinaryResource screenshotResource = BinaryResources.create()
        .withFilename("screenshot.jpg")
        .withContentType("image/jpeg")
        .withContent(bytes)
        .build();

    // wrap BinaryResource in MailAttachment
    MailAttachment screenshotAttachment = new MailAttachment(screenshotResource);

    // prepare Scout MailMessage
    MailMessage mailMessage = BEANS.get(MailMessage.class)
        .withSender(BEANS.get(MailParticipant.class).withName("sender").withEmail("me@example.com"))
        .addToRecipient(BEANS.get(MailParticipant.class).withName("recipient").withEmail("somebody@example.com"))
        .withAttachment(screenshotAttachment)
        .withSubject("Screenshot")
        .withBodyPlainText("Dear recipient,\n\nPlease have a look at my screenshot!\n\nRegards,\nsender");

    // convert MailMessage to MimeMessage
    CharsetSafeMimeMessage mimeMessage = BEANS.get(MailHelper.class).createMimeMessage(mailMessage);
    // end::SmtpHelper.prepareMimeMessage[]

    SmtpServerConfig smtpServerConfig = BEANS.get(SmtpServerConfig.class);

    // tag::SmtpHelper.sendWithSmtpServerConfig[]
    BEANS.get(SmtpHelper.class).sendMessage(smtpServerConfig, mimeMessage);
    // end::SmtpHelper.sendWithSmtpServerConfig[]

    Session session = null;
    String password = null;

    // tag::SmtpHelper.sendWithSession[]
    // The password has to be provided additionally as it is not stored in the session object.
    BEANS.get(SmtpHelper.class).sendMessage(session, password, mimeMessage);
    // end::SmtpHelper.sendWithSession[]
  }
}
