/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.server.sql;

import java.util.Set;

import jakarta.annotation.PostConstruct;

import org.eclipse.scout.contacts.server.sql.DatabaseProperties.DatabaseAutoCreateProperty;
import org.eclipse.scout.contacts.server.sql.DatabaseProperties.DatabaseAutoPopulateProperty;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.CreateImmediately;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tag::service[]
@ApplicationScoped
@CreateImmediately
public class DatabaseSetupService implements IDataStoreService {
  private static final Logger LOG = LoggerFactory.getLogger(DatabaseSetupService.class);

  @PostConstruct
  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(DatabaseAutoCreateProperty.class)) {
      try {
        BEANS.get(DerbySqlService.class).createDB();
        RunContext context = BEANS.get(SuperUserRunContextProducer.class).produce();
        IRunnable runnable = () -> {
          createOrganizationTable();
          createPersonTable();
        };

        context.run(runnable);
      }
      catch (RuntimeException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  public void createOrganizationTable() {
    if (!getExistingTables().contains("ORGANIZATION")) {
      SQL.insert(SQLs.ORGANIZATION_CREATE_TABLE);
      LOG.info("Database table 'ORGANIZATION' created");

      if (CONFIG.getPropertyValue(DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE + SQLs.ORGANIZATION_VALUES_01);
        SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE + SQLs.ORGANIZATION_VALUES_02);
        LOG.info("Database table 'ORGANIZATION' populated with sample data");
      }
    }
  }

  public void createPersonTable() {
    if (!getExistingTables().contains("PERSON")) {
      SQL.insert(SQLs.PERSON_CREATE_TABLE);
      LOG.info("Database table 'PERSON' created");

      if (CONFIG.getPropertyValue(DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_01);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_02);
        // end::service[]
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_03);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_04);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_05);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_06);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_07);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_08);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_09);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_10);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_11);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_12);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_13);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_14);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_15);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_16);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_17);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_18);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_19);
        SQL.insert(SQLs.PERSON_INSERT_SAMPLE + SQLs.PERSON_VALUES_20);
        // tag::service[]
        LOG.info("Database table 'PERSON' populated with sample data");
      }
    }
  }

  private Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables)); // <1>
    return CollectionUtility.hashSet(tables.getValue());
  }
  // end::service[]

  @Override
  public void dropDataStore() {
    SQL.update(SQLs.PERSON_DROP_TABLE);
    SQL.update(SQLs.ORGANIZATION_DROP_TABLE);
  }

  @Override
  public void createDataStore() {
    createOrganizationTable();
    createPersonTable();
  }
  // tag::service[]
}
// end::service[]
