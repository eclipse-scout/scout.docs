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
package org.eclipsescout.demo.minicrm.server.ui.desktop.form;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.CompanyFormData;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.CreateCompanyPermission;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.ICompanyService;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.ReadCompanyPermission;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.UpdateCompanyPermission;

public class CompanyService extends AbstractService implements ICompanyService {

  @Override
  public CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //no business logic
    return formData;
  }

  @Override
  public CompanyFormData create(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto("" +
        "SELECT MAX(COMPANY_NR)+1 " +
        "FROM   COMPANY " +
        "INTO   :companyNr"
        , formData);

    SQL.insert("" +
        "INSERT INTO COMPANY (COMPANY_NR, SHORT_NAME, NAME, TYPE_UID, RATING_UID) " +
        "VALUES (:companyNr, :shortName, :name, :companyTypeGroup, :companyRating)"
        , formData);

    return formData;
  }

  @Override
  public CompanyFormData load(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto("" +
        "SELECT SHORT_NAME, " +
        "       NAME," +
        "       TYPE_UID, " +
        "       RATING_UID " +
        "FROM   COMPANY " +
        "WHERE  COMPANY_NR = :companyNr " +
        "INTO   :shortName," +
        "       :name, " +
        "       :companyTypeGroup, " +
        "       :companyRating "
        , formData);

    return formData;
  }

  @Override
  public CompanyFormData store(CompanyFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update("" +
        "UPDATE COMPANY " +
        " SET SHORT_NAME = :shortName, " +
        "     NAME = :name, " +
        "     TYPE_UID = :companyTypeGroup, " +
        "     RATING_UID = :companyRating " +
        "WHERE  COMPANY_NR = :companyNr "
        , formData);

    return formData;
  }

  @Override
  public void delete(Long companyNr) throws ProcessingException {
    if (!ACCESS.check(new UpdateCompanyPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.delete("DELETE FROM COMPANY " +
        "WHERE  COMPANY_NR = :companyNr",
        new NVPair("companyNr", companyNr));
  }
}
