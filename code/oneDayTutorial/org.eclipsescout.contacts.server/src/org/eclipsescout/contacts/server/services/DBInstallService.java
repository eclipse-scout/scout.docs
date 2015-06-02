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
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.services.IDBInstallService;

/**
 * @author mzi
 */
public class DBInstallService extends AbstractService implements IDBInstallService {

  private boolean m_doSetup = true;

  public void setDoSetup(boolean doSetup) {
    m_doSetup = doSetup;
  }

  @Override
  public void installStorage() throws ProcessingException {
    boolean addInitialData = true;
    if (m_doSetup) {
      Set<String> tables = getExistingTables();
      createCompanyTable(tables, addInitialData);
      createPersonTable(tables, addInitialData);
      createUsersParamTable(tables, addInitialData);
    }
  }

  private Set<String> getExistingTables() throws ProcessingException {
    Object[][] existingTables = SQL.select("SELECT tablename FROM sys.systables");
    HashSet<String> result = new HashSet<String>(existingTables.length);

    for (Object[] row : existingTables) {
      String table = (row[0] + "").toUpperCase();
      result.add(table);
    }

    return result;
  }

  private void createCompanyTable(Set<String> tables, boolean addInitialData) throws ProcessingException {
    if (!tables.contains("COMPANY")) {
      SQL.insert("CREATE TABLE COMPANY ("
          + " company_id VARCHAR(64) NOT NULL CONSTRAINT COMPANY_PK PRIMARY KEY, "
          + " name VARCHAR(64), "
          + " location VARCHAR(64), "
          + " url VARCHAR(64))");

      if (addInitialData) {
        SQL.insert("INSERT INTO COMPANY (company_id, name, location, url) "
            + "VALUES (:company_id, 'Alice''s Adventures in Wonderland', 'London, England', 'http://en.wikipedia.org/wiki/Alice%27s_Adventures_in_Wonderland')",
            new NVPair("company_id", UUID.randomUUID().toString()));

        SQL.insert("INSERT INTO COMPANY (company_id, name, location, url) "
            + "VALUES (:company_id, 'BSI Business Systems Integration AG', 'Daettwil, Baden, Switzerland', 'http://www.bsiag.com')",
            new NVPair("company_id", UUID.randomUUID().toString()));
      }
    }
  }

  private void createPersonTable(Set<String> tables, boolean addInitialData) throws ProcessingException {
    if (!tables.contains("PERSON")) {
      SQL.insert("CREATE TABLE PERSON ("
          + " person_id VARCHAR(64) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY, "
          + " company_id VARCHAR(64), "
          + " first_name VARCHAR(64), "
          + " last_name VARCHAR(64), "
          + " headline VARCHAR(512), "
          + " location VARCHAR(512), "
          + " date_of_birth DATE, "
          + " picture_url VARCHAR(512), "
          + " CONSTRAINT COMPANY_FK FOREIGN KEY (company_id) REFERENCES COMPANY (company_id))");

      if (addInitialData) {
        SQL.insert("INSERT INTO PERSON (person_id, first_name, headline, location, company_id, picture_url, date_of_birth)"
            + "VALUES (:person_id, 'Alice', 'The curious girl', 'Daresbury, Cheshire, England', "
            + "(SELECT company_id FROM COMPANY WHERE name = 'Alice''s Adventures in Wonderland'), 'http://www.uergsel.de/uploads/Alice.png', :dob)",
            new NVPair("person_id", UUID.randomUUID().toString()),
            new NVPair("dob", DateUtility.parse("26.11.1865", "dd.mm.yyyy")));
      }
    }
  }

  private void createUsersParamTable(Set<String> tables, boolean addInitialData) throws ProcessingException {
    if (!tables.contains("USERS_PARAM")) {
      SQL.insert("CREATE TABLE USERS_PARAM ("
          + " username VARCHAR(32) NOT NULL, "
          + " param VARCHAR(32) NOT NULL, "
          + " value VARCHAR(512), "
          + " CONSTRAINT PARAM_PK PRIMARY KEY (username, param))");

      if (addInitialData) {
        // nop
      }
    }
  }
}
