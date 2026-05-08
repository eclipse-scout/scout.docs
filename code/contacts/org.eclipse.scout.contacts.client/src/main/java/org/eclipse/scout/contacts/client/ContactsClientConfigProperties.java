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

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public final class ContactsClientConfigProperties {

  private ContactsClientConfigProperties() {
  }

  public static class UserDomainProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "contacts.userDomain";
    }

    @Override
    public String description() {
      return "If a domain is set, the user image is loaded from gravatar by using the following email: userId@userDomain (see UserForm.java)";
    }
  }
}
