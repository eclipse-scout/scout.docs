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
package org.eclipse.scout.tutorial.jaxws.server.services.ws.provider;

import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.BeanArrayHolder;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.jaxws.annotation.ScoutWebService;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.tutorial.jaxws.services.ws.companywebservice.Company;
import org.eclipse.scout.tutorial.jaxws.services.ws.companywebservice.CompanyWebServicePortType;

@ScoutWebService
@WebService(endpointInterface = "org.eclipse.scout.tutorial.jaxws.services.ws.companywebservice.CompanyWebServicePortType")
public class CompanyWebService implements CompanyWebServicePortType {

  @Override
  public List<Company> getCompanies() {
    // holder to create a company bean for each company record in database
    BeanArrayHolder<Company> companyBeanHolder = new BeanArrayHolder<Company>(Company.class);
    try {
      // run SQL
      SQL.selectInto("" +
          "SELECT   NAME, " +
          "         SYMBOL " +
          "FROM     COMPANY " +
          "INTO     :{name}, " +
          "         :{symbol}"
          , companyBeanHolder
          );
    }
    catch (ProcessingException e) {
      ScoutLogManager.getLogger(CompanyWebService.class).error("failed to load company data", e);
    }
    return Arrays.asList(companyBeanHolder.getBeans());
  }
}
