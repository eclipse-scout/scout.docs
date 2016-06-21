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
package org.eclipse.scout.contacts.events.server;

import java.util.Set;

import org.eclipse.scout.contacts.events.server.sql.SQLs;
import org.eclipse.scout.contacts.events.shared.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.events.shared.person.PersonTablePageDataExtension;
import org.eclipse.scout.contacts.server.sql.DatabaseProperties;
import org.eclipse.scout.contacts.server.sql.IDataStoreService;
import org.eclipse.scout.contacts.server.sql.SuperUserRunContextProducer;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.context.RunContext;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(20)
public class PlatformListener implements IPlatformListener, IDataStoreService {
  private static final Logger LOG = LoggerFactory.getLogger(PlatformListener.class);

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      autoCreateDatabase();
      registerExtensions();
    }
  }

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoCreateProperty.class)) {
      try {
        RunContext context = BEANS.get(SuperUserRunContextProducer.class).produce();
        IRunnable runnable = new IRunnable() {

          @Override
          public void run() throws Exception {
            createEventTable();
            createParticipantTable();
          }
        };

        context.run(runnable);
      }
      catch (RuntimeException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  private void registerExtensions() {
    IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);

    // Register DTO core extensions
    extensionRegistry.register(PersonTablePageDataExtension.class);
    extensionRegistry.register(PersonFormTabExtensionData.class);
  }

  public void createEventTable() {
    if (!getExistingTables().contains("EVENT")) {
      SQL.insert(SQLs.EVENT_CREATE_TABLE);
      LOG.info("Database table 'EVENT' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.EVENT_INSERT_SAMPLE + SQLs.EVENT_INSERT_VALUES_01);
        SQL.insert(SQLs.EVENT_INSERT_SAMPLE + SQLs.EVENT_INSERT_VALUES_02);
        LOG.info("Database table 'EVENT' populated with sample data");
      }
    }
  }

  protected void createParticipantTable() {
    if (!getExistingTables().contains("PARTICIPANT")) {
      SQL.insert(SQLs.PARTICIPANT_CREATE_TABLE);
      LOG.info("Database table 'PARTICIPANT' created");

      if (CONFIG.getPropertyValue(DatabaseProperties.DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE + SQLs.PARTICIPANT_INSERT_VALUES_01);
        SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE + SQLs.PARTICIPANT_INSERT_VALUES_02);
        SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE + SQLs.PARTICIPANT_INSERT_VALUES_03);
        LOG.info("Database table 'PARTICIPANT' populated with sample data");
      }
    }
  }

  private Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  @Override
  public void dropDataStore() {
    SQL.update(SQLs.PARTICIPANT_DROP_TABLE);
    SQL.update(SQLs.EVENT_DROP_TABLE);
  }

  @Override
  public void createDataStore() {
    createEventTable();
    createParticipantTable();
  }
}
