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

import java.io.Serializable;
import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.eclipse.scout.rt.server.notification.ICoalescer;
import org.eclipse.scout.rt.shared.services.common.bookmark.BookmarkChangedClientNotification;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.shared.services.common.code.CodeService;
import org.eclipse.scout.rt.shared.services.common.code.ICode;

/**
 * Snippets for Documentation
 */
@SuppressWarnings("unused")
public class ClientNotificationSnippet {

  void snippet_publishClientNotification() {
    // tag::PublishClientNotification[]
    BEANS.get(ClientNotificationRegistry.class).putForUser("admin", new PersonTableChangedNotification());
    // end::PublishClientNotification[]
  }

  void snippet_codeClientNotification() {
    // tag::cn_reloadCodeType[]
    CodeService cs = BEANS.get(CodeService.class);
    cs.reloadCodeType(UiThemeCodeType.class);

    //client-side reload triggered by client notifications is finished
    List<? extends ICode<String>> reloadedCodes = cs.getCodeType(UiThemeCodeType.class).getCodes();
    // end::cn_reloadCodeType[]
  }

//tag::cn_bookmarkCoalescer[]
  public class BookmarkNotificationCoalescer implements ICoalescer<BookmarkChangedClientNotification> {

    @Override
    public List<BookmarkChangedClientNotification> coalesce(List<BookmarkChangedClientNotification> notifications) {
      // reduce to one
      return CollectionUtility.arrayList(CollectionUtility.firstElement(notifications));
    }
  }
// end::cn_bookmarkCoalescer[]

  /**
   * Sample notification
   */
  static class PersonTableChangedNotification implements Serializable {

    private static final long serialVersionUID = 1L;
  }

  @ClassId("9bf8088c-9701-4c3b-9b1f-41488ca048b9")
  static class UiThemeCodeType extends AbstractCodeType<Long, String> {

    private static final long serialVersionUID = 1L;
    static final Long ID = 10000L;

    public UiThemeCodeType() {
      super();
    }

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("UiTheme");
    }

    @Override
    protected String getConfiguredTextPlural() {
      return TEXTS.get("UiThemes");
    }

    @Override
    public Long getId() {
      return ID;
    }

  }

}
