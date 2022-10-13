/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.server.sql;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractSubjectConfigProperty;

// tag::structure[]
public class DatabaseProperties {

  public static class DatabaseAutoCreateProperty extends AbstractBooleanConfigProperty {
    // defines default value and key

    @Override
    public Boolean getDefaultValue() {
      return Boolean.TRUE; // <1>
    }

    @Override
    public String getKey() {
      return "contacts.database.autocreate"; // <2>
    }

    @Override
    public String description() {
      return "Specifies if the contacts database should automatically be created if it does not exist yet. The default value is true.";
    }
  }

  public static class DatabaseAutoPopulateProperty extends AbstractBooleanConfigProperty {
    // defines default value and key
    // end::structure[]

    @Override
    public Boolean getDefaultValue() {
      return Boolean.TRUE;
    }

    @Override
    public String getKey() {
      return "contacts.database.autopopulate";
    }

    @Override
    public String description() {
      return "Specifies if test data should be inserted into a newly created contact database. The default value is true.";
    }
    // tag::structure[]
  }

  public static class JdbcMappingNameProperty extends AbstractStringConfigProperty {
    // defines default value and key
    // end::structure[]

    @Override
    public String getDefaultValue() {
      return "jdbc:derby:memory:contacts-database";
    }

    @Override
    public String getKey() {
      return "contacts.database.jdbc.mappingName";
    }

    @Override
    public String description() {
      return "JDBC mapping name for the contacts database. The default value is 'jdbc:derby:memory:contacts-database'.";
    }
    // tag::structure[]
  }

  public static class SuperUserSubjectProperty extends AbstractSubjectConfigProperty {
    // defines default value and key
    // end::structure[]

    @Override
    public Subject getDefaultValue() {
      return convertToSubject("system");
    }

    @Override
    public String getKey() {
      return "contacts.superuser";
    }

    @Override
    public String description() {
      return "Contacts super user subject name. The default is 'contacts.superuser'.";
    }
    // tag::structure[]
  }
}
// end::structure[]
