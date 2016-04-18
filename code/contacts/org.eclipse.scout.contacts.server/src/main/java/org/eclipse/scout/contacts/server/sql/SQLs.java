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
package org.eclipse.scout.contacts.server.sql;

// tag::createDB[]
public interface SQLs {

  String SELECT_TABLE_NAMES = ""
      + "SELECT   UPPER(tablename) "
      + "FROM     sys.systables "
      + "INTO     :result";

  String ORGANIZATION_CREATE_TABLE = ""
      + "CREATE   TABLE ORGANIZATION "
      + "         (organization_id VARCHAR(64) NOT NULL CONSTRAINT ORGANIZATION_PK PRIMARY KEY, "
      + "          name VARCHAR(64), "
      + "          logo_url VARCHAR(512), "
      + "          url VARCHAR(64), "
      + "          street VARCHAR(64), "
      + "          city VARCHAR(64), "
      + "          country VARCHAR(2), "
      + "          phone VARCHAR(20), "
      + "          email VARCHAR(64), "
      + "          comment VARCHAR(1024)"
      + "         )";

  String PERSON_CREATE_TABLE = ""
      + "CREATE   TABLE PERSON "
      + "         (person_id VARCHAR(64) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY, "
      + "          first_name VARCHAR(64), "
      + "          last_name VARCHAR(64), "
      + "          picture_url VARCHAR(512), "
      + "          date_of_birth DATE, "
      + "          gender VARCHAR(1), "
      + "          street VARCHAR(64), "
      + "          city VARCHAR(64), "
      + "          country VARCHAR(2), "
      + "          phone VARCHAR(20), "
      + "          mobile VARCHAR(20), "
      + "          email VARCHAR(64), "
      + "          organization_id VARCHAR(64), "
      + "          position VARCHAR(512), "
      + "          phone_work VARCHAR(20), "
      + "          email_work VARCHAR(64), "
      + "          comment VARCHAR(1024), "
      + "          CONSTRAINT ORGANIZATION_FK FOREIGN KEY (organization_id) REFERENCES ORGANIZATION (organization_id)"
      + "         )";
// end::createDB[]

  String PERSON_LOOKUP = ""
      + "SELECT   person_id, "
      + "         CASE "
      + "           WHEN first_name IS null "
      + "            THEN last_name "
      + "           WHEN last_name IS null "
      + "            THEN first_name "
      + "           ELSE "
      + "            first_name || ' ' || last_name "
      + "         END "
      + "FROM     PERSON "
      + "WHERE    1 = 1 "
      + "<key>    AND person_id = :key</key> "
      + "<text>   AND (UPPER(first_name) LIKE UPPER('%'||:text||'%') "
      + "         OR UPPER(last_name) LIKE UPPER('%'||:text||'%')) "
      + "</text>"
      + "<all> </all>";

  String ORGANIZATION_LOOKUP = ""
      + "SELECT   organization_id, "
      + "         name "
      + "FROM     ORGANIZATION "
      + "WHERE    1 = 1 "
      + "<key>    AND organization_id = :key</key> "
      + "<text>   AND UPPER(name) LIKE UPPER(:text||'%') </text> "
      + "<all></all>";

  String AND_LIKE_CAUSE = "AND UPPER(%s) LIKE UPPER(:%s || '%s')";

  String ORGANIZATION_PAGE_SELECT = ""
      + "SELECT   organization_id, "
      + "         name, "
      + "         city, "
      + "         country, "
      + "         url "
      + "FROM     ORGANIZATION";

  String ORGANIZATION_PAGE_DATA_SELECT_INTO = ""
      + "INTO     :{page.organizationId}, "
      + "         :{page.name}, "
      + "         :{page.city}, "
      + "         :{page.country}, "
      + "         :{page.homepage}";

  String ORGANIZATION_INSERT = ""
      + "INSERT   INTO "
      + "ORGANIZATION  (organization_id) "
      + "VALUES   (:organizationId)";

  String ORGANIZATION_SELECT = ""
      + "SELECT   name, "
      + "         logo_url, "
      + "         url, "
      + "         phone, "
      + "         email, "
      + "         street, "
      + "         city, "
      + "         country, "
      + "         comment "
      + "FROM     ORGANIZATION "
      + "WHERE    organization_id = :organizationId "
      + "INTO     :name, "
      + "         :logoBox.pictureUrl, "
      + "         :homepage, "
      + "         :phone, "
      + "         :email, "
      + "         :addressBox.street, "
      + "         :addressBox.city, "
      + "         :addressBox.country, "
      + "         :comments";

  String ORGANIZATION_UPDATE = ""
      + "UPDATE   ORGANIZATION "
      + "SET      name = :name, "
      + "         logo_url = :logoBox.pictureUrl, "
      + "         url = :homepage, "
      + "         phone = :phone, "
      + "         email = :email, "
      + "         street = :addressBox.street, "
      + "         city = :addressBox.city, "
      + "         country = :addressBox.country, "
      + "         comment = :comments "
      + "WHERE    organization_id = :organizationId";

  String PERSON_PAGE_SELECT = ""
      + "SELECT   person_id, "
      + "         first_name, "
      + "         last_name, "
      + "         city, "
      + "         country, "
      + "         phone, "
      + "         mobile, "
      + "         email, "
      + "         organization_id "
      + "FROM     PERSON";

  String PERSON_PAGE_DATA_SELECT_INTO = ""
      + "INTO     :{page.personId}, "
      + "         :{page.firstName}, "
      + "         :{page.lastName}, "
      + "         :{page.city}, "
      + "         :{page.country}, "
      + "         :{page.phone}, "
      + "         :{page.mobile}, "
      + "         :{page.email}, "
      + "         :{page.organization}";

  String PERSON_INSERT = ""
      + "INSERT   INTO "
      + "PERSON  (person_id) "
      + "VALUES   (:personId)";

  String PERSON_SELECT = ""
      + "SELECT   first_name, "
      + "         last_name, "
      + "         picture_url, "
      + "         date_of_birth, "
      + "         gender, "
      + "         phone, "
      + "         mobile, "
      + "         email, "
      + "         street, "
      + "         city, "
      + "         country, "
      + "         position, "
      + "         organization_id, "
      + "         phone_work, "
      + "         email_work, "
      + "         comment "
      + "FROM     PERSON "
      + "WHERE    person_id = :personId "
      + "INTO     :firstName, "
      + "         :lastName, "
      + "         :pictureBox.pictureUrl, "
      + "         :dateOfBirth, "
      + "         :genderGroup, "
      + "         :phone, "
      + "         :mobile, "
      + "         :email, "
      + "         :addressBox.street, "
      + "         :addressBox.city, "
      + "         :addressBox.country, "
      + "         :position, "
      + "         :organization, "
      + "         :phoneWork, "
      + "         :emailWork, "
      + "         :comments";

  String PERSON_UPDATE = ""
      + "UPDATE   PERSON "
      + "SET      first_name = :firstName, "
      + "         last_name = :lastName, "
      + "         picture_url = :pictureBox.pictureUrl, "
      + "         date_of_birth = :dateOfBirth, "
      + "         gender = :genderGroup, "
      + "         phone  = :phone, "
      + "         mobile = :mobile, "
      + "         email = :email, "
      + "         street = :addressBox.street, "
      + "         city = :addressBox.city, "
      + "         country = :addressBox.country, "
      + "         position = :position, "
      + "         organization_id = :organization, "
      + "         phone_work = :phoneWork, "
      + "         email_work = :emailWork, "
      + "         comment = :comments "
      + "WHERE    person_id = :personId";

  String ORGANIZATION_INSERT_SAMPLE_DATA = ""
      + "INSERT   INTO ORGANIZATION "
      + "        (organization_id, "
      + "         name, "
      + "         city, "
      + "         country, "
      + "         url, "
      + "         logo_url) "
      + "VALUES  (:organizationUuid, "
      + "         :name, "
      + "         :city, "
      + "         :country, "
      + "         :url, "
      + "         :logoUrl)";

  String PERSON_INSERT_SAMPLE_DATA = ""
      + "INSERT   INTO PERSON "
      + "         (person_id, "
      + "          first_name, "
      + "          last_name, "
      + "          picture_url, "
      + "          date_of_birth, "
      + "          gender, "
      + "          city, "
      + "          country, "
      + "          position, "
      + "          organization_id) "
      + "VALUES   (:personUuid, "
      + "          :firstName, "
      + "          :lastName, "
      + "          :pictureUrl, "
      + "          :dob, "
      + "          :gender, "
      + "          :city, "
      + "          :country, "
      + "          :position, "
      + "          :organizationUuid)";
// tag::createDB[]
}
// end::createDB[]
