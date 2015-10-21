/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.event.server.sql;

public interface SQLs {

  String CONTACT_EVENT_SELECT = ""
      + "SELECT       e.event_id, "
      + "             title, "
      + "             date_start, "
      + "             city, "
      + "             country "
      + "FROM         EVENT e, "
      + "             PARTICIPANT p "
      + "WHERE        e.event_id = p.event_id "
      + "AND          contact_id = :contactId "
      + "INTO         :{events.id}, "
      + "             :{events.title}, "
      + "             :{events.starts}, "
      + "             :{events.city}, "
      + "             :{events.country}";

  String EVENT_PAGE_DATA_SELECT = ""
      + "SELECT       e.event_id, "
      + "             e.title, "
      + "             e.date_start, "
      + "             e.date_end, "
      + "             e.city, "
      + "             e.country, "
      + "             e.url, "
      + "             (SELECT   COUNT(1) "
      + "             FROM     PARTICIPANT p "
      + "             WHERE    P.event_id = e.event_id) "
      + "FROM         EVENT e";

  String EVENT_PAGE_DATA_WHERE_CLAUSE = ""
      + "AND          e.event_id in (SELECT DISTINCT p.event_id "
      + "                            FROM    PARTICIPANT p, "
      + "                                    CONTACT c "
      + "                            WHERE   p.contact_id = c.contact_id "
      + "                            AND     company_id = :companyId)";

  String EVENT_PAGE_DATA_INTO = ""
      + "INTO         :{page.eventId}, "
      + "             :{page.title}, "
      + "             :{page.starts}, "
      + "             :{page.ends}, "
      + "             :{page.city}, "
      + "             :{page.country}, "
      + "             :{page.homepage}, "
      + "             :{page.participants}";

  String EVENT_INSERT = ""
      + "INSERT     INTO "
      + "EVENT      (event_id) "
      + "VALUES     (:eventId)";

  String EVENT_SELECT = ""
      + "SELECT       title, "
      + "             date_start, "
      + "             date_end, "
      + "             city, "
      + "             country, "
      + "             url, "
      + "             comment "
      + "FROM         EVENT "
      + "WHERE        event_id = :eventId "
      + "INTO         :title, "
      + "             :starts, "
      + "             :ends, "
      + "             :locationBox.city, "
      + "             :locationBox.country, "
      + "             :homepage, "
      + "             :comments";

  String EVENT_UPDATE = ""
      + "UPDATE       EVENT "
      + "SET          title = :title, "
      + "             date_start = :starts, "
      + "             date_end = :ends, "
      + "             url = :homepage, "
      + "             city = :locationBox.city, "
      + "             country = :locationBox.country, "
      + "             comment = :comments "
      + "WHERE        event_id = :eventId";

  String EVENT_PARTICIPANTS_SELECT = ""
      + "SELECT       c.contact_id, "
      + "             c.first_name, "
      + "             c.last_name, "
      + "             c.company_id "
      + "FROM         PARTICIPANT p "
      + "LEFT JOIN    CONTACT c "
      + "ON           c.contact_id = p.contact_id "
      + "WHERE        p.event_id = :eventId "
      + "INTO         :{participants.contactId}, "
      + "             :{participants.firstName}, "
      + "             :{participants.lastName}, "
      + "             :{participants.company}";

  String EVENT_PARTICIPANTS_DELETE = ""
      + "DELETE       FROM PARTICIPANT "
      + "WHERE        event_id = :eventId "
      + "AND          contact_id = :{contactId}";

  String EVENT_PARTICIPANTS_INSERT = ""
      + "INSERT       INTO "
      + "PARTICIPANT  (event_id, "
      + "              contact_id) "
      + "VALUES       (:eventId, "
      + "              :{contactId})";

  String EVENT_COUNT_BY_CONTACT = ""
      + "SELECT       contact_id, "
      + "             COUNT(event_id) "
      + "FROM         PARTICIPANT "
      + "GROUP BY     contact_id "
      + "INTO         :{bean.contactId}, "
      + "             :{bean.eventCount}";

  String SELECT_TABLE_NAMES = ""
      + "SELECT       UPPER(tablename) "
      + "FROM         sys.systables "
      + "INTO         :result";

  String EVENT_CREATE_TABLE = ""
      + "CREATE       "
      + "TABLE        EVENT "
      + "            (event_id VARCHAR(64) NOT NULL CONSTRAINT EVENT_PK PRIMARY KEY, "
      + "             title VARCHAR(64), "
      + "             date_start TIMESTAMP, "
      + "             date_end TIMESTAMP, "
      + "             city VARCHAR(64), "
      + "             country VARCHAR(2), "
      + "             url VARCHAR(64), "
      + "             comment VARCHAR(1024))";

  String COMPANY_INSERT_SAMPLE_DATA_1 = ""
      + "INSERT       "
      + "INTO         EVENT "
      + "            (event_id, "
      + "             title, "
      + "             date_start, "
      + "             date_end, "
      + "             city, "
      + "             country, "
      + "             url) "
      + "VALUES      (:eventId, "
      + "             'EclipseCon 2015', "
      + "             :starts, "
      + "             :ends, "
      + "             'San Francisco', "
      + "             'US', "
      + "             'https://www.eclipsecon.org/na2015/')";

  String COMPANY_INSERT_SAMPLE_DATA_2 = ""
      + "INSERT       "
      + "INTO         EVENT "
      + "            (event_id, "
      + "             title, "
      + "             date_start, "
      + "             date_end, "
      + "             city, "
      + "             country, "
      + "             url) "
      + "VALUES      (:eventId, "
      + "             'JavaLand 2015', "
      + "             :starts, "
      + "             :ends, "
      + "             'Bruehl', "
      + "             'DE', "
      + "             'http://www.javaland.eu/javaland-2015/')";

  String COMPANY_INSERT_SAMPLE_DATA_3 = ""
      + "INSERT       "
      + "INTO         EVENT "
      + "            (event_id, "
      + "             title, "
      + "             date_start, "
      + "             date_end, "
      + "             city, "
      + "             country, "
      + "             url) "
      + "VALUES      (:eventId, "
      + "             'Eclipse DemoCamp Munich 2015', "
      + "             :starts, "
      + "             :ends, "
      + "             'Munich', "
      + "             'DE', "
      + "             'https://wiki.eclipse.org/Eclipse_DemoCamps_Mars_2015/Munich')";

  String PARTICIPANT_CREATE_TABLE = ""
      + "CREATE       "
      + "TABLE        PARTICIPANT "
      + "             (event_id VARCHAR(64) NOT NULL, "
      + "              contact_id VARCHAR(64) NOT NULL, "
      + "PRIMARY KEY  (event_id, contact_id))";

  String PARTICIPANT_INSERT_SAMPLE_DATA_1 = ""
      + "INSERT       "
      + "INTO         PARTICIPANT "
      + "            (event_id, "
      + "             contact_id) "
      + "VALUES      ((SELECT   event_id "
      + "              FROM     EVENT "
      + "              WHERE    title = 'Eclipse DemoCamp Munich 2015'), "
      + "             (SELECT   contact_id "
      + "              FROM     CONTACT "
      + "              WHERE    first_name = 'Rabbit'))";

  String PARTICIPANT_INSERT_SAMPLE_DATA_2 = ""
      + "INSERT       "
      + "INTO         PARTICIPANT "
      + "            (event_id, "
      + "             contact_id) "
      + "VALUES      ((SELECT   event_id "
      + "              FROM     EVENT "
      + "              WHERE    title = 'Eclipse DemoCamp Munich 2015'), "
      + "             (SELECT   contact_id "
      + "              FROM     CONTACT "
      + "              WHERE    first_name = 'Alice'))";
}
