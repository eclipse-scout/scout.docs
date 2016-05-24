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
package org.eclipse.scout.contacts.server;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractSubjectConfigProperty;

// tag::databaseProperties[]
public final class ConfigProperties {

  private ConfigProperties() {
  }

  public static class JdbcMappingNameProperty extends AbstractStringConfigProperty {

    @Override
    protected String getDefaultValue() {
      return "jdbc:derby:memory:contacts-database";
    }

    @Override
    public String getKey() {
      return "contacts.database.jdbc.mapping.name";
    }
  }

  public static class DatabaseAutoCreateProperty extends AbstractBooleanConfigProperty {

    @Override
    protected Boolean getDefaultValue() {
      return Boolean.TRUE;
    }

    @Override
    public String getKey() {
      return "contacts.database.autocreate";
    }
  }

  // end::databaseProperties[]

  public static class DatabaseAutoPopulateProperty extends AbstractBooleanConfigProperty {

    @Override
    protected Boolean getDefaultValue() {
      return Boolean.TRUE;
    }

    @Override
    public String getKey() {
      return "contacts.database.autopopulate";
    }
  }

  // tag::databaseProperties[]
  public static class SuperUserSubjectProperty extends AbstractSubjectConfigProperty {

    @Override
    protected Subject getDefaultValue() {
      return convertToSubject("system");
    }

    @Override
    public String getKey() {
      return "contacts.superuser";
    }
  }
}
//tag::databaseProperties[]
