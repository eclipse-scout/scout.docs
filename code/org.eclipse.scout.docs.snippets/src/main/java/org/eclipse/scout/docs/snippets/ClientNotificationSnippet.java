package org.eclipse.scout.docs.snippets;

import java.io.Serializable;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;

/**
 * Snippets for Documentation
 */
public class ClientNotificationSnippet {

  void snippet_publishClientNotification() {
    // tag::PublishClientNotification[]
    BEANS.get(ClientNotificationRegistry.class).putForUser("admin", new PersonTableChangedNotification());
    // end::PublishClientNotification[]
  }

  /**
   * Sample notification
   */
  class PersonTableChangedNotification implements Serializable {
    private static final long serialVersionUID = 1L;
  }

}
