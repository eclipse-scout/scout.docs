/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.shared;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;

public final class ContactsSharedConfigProperties {

  private ContactsSharedConfigProperties() {
  }

  public static class ReadOnlyProperty extends AbstractBooleanConfigProperty {

    @Override
    public String getKey() {
      return "contacts.readOnly";
    }

    @Override
    public String description() {
      return "Flag to activate a read-only mode when contacts application is deployed as public available demo application";
    }

    @Override
    public Boolean getDefaultValue() {
      return false;
    }
  }
}
