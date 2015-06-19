/**
 *
 */
package org.eclipsescout.contacts.server.services;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.commons.DateUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.services.IServerStartupService;

/**
 * @author mzi
 */
public class ServerStartupService extends AbstractService implements IServerStartupService {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(ServerStartupService.class);

  private boolean m_doSetup = true;
  private boolean m_addInitialData = true;

  @Override
  public void init() throws ProcessingException {
    if (doSetup()) {
      Set<String> tables = getExistingTables();
      createCompanyTable(tables, addInitialData());
      createContactTable(tables, addInitialData());
    }

    LOG.info("DB install completed");
  }

  protected Set<String> getExistingTables() throws ProcessingException {
    Object[][] existingTables = SQL.select(TEXTS.get("SqlSelectTableNames"));
    HashSet<String> result = new HashSet<String>(existingTables.length);

    for (Object[] row : existingTables) {
      String table = (row[0] + "").toUpperCase();
      result.add(table);
    }

    return result;
  }

  private void createCompanyTable(Set<String> tables, boolean addInitialData) throws ProcessingException {
    if (!tables.contains("COMPANY")) {
      SQL.insert(TEXTS.get("SqlCompanyCreateTable"));

      if (addInitialData) {
        SQL.insert(TEXTS.get("SqlCompanyInsertExample1"), new NVPair("company_id", UUID.randomUUID().toString()));
        SQL.insert(TEXTS.get("SqlCompanyInsertExample2"), new NVPair("company_id", UUID.randomUUID().toString()));
      }
    }
  }

  private void createContactTable(Set<String> tables, boolean addInitialData) throws ProcessingException {
    if (!tables.contains("CONTACT")) {
      SQL.insert(TEXTS.get("SqlContactCreateTable"));

      if (addInitialData) {
        SQL.insert(TEXTS.get("SqlContactInsertExample1"), new NVPair("person_id", UUID.randomUUID().toString()), new NVPair("dob", DateUtility.parse("26.11.1865", "dd.MM.yyyy")));
        SQL.insert(TEXTS.get("SqlContactInsertExample2"), new NVPair("person_id", UUID.randomUUID().toString()), new NVPair("dob", DateUtility.parse("26.11.1861", "dd.MM.yyyy")));
      }
    }
  }

  public boolean doSetup() {
    return m_doSetup;
  }

  public void setDoSetup(boolean doSetup) {
    m_doSetup = doSetup;
  }

  public boolean addInitialData() {
    return m_addInitialData;
  }

  public void setAddInitialData(boolean addInitialData) {
    m_addInitialData = addInitialData;
  }

}
