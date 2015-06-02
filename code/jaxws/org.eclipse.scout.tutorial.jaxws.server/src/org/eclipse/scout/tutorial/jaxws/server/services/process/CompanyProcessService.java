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
package org.eclipse.scout.tutorial.jaxws.server.services.process;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.tutorial.jaxws.server.services.ws.consumer.StockQuoteServiceSoapWebServiceClient;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.CompanyFormData;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.ICompanyProcessService;

import com.nexus6studio.services.StockQuote;
import com.nexus6studio.services.StockQuoteServiceSoap;

public class CompanyProcessService extends AbstractService implements ICompanyProcessService {

  @Override
  public CompanyFormData prepareCreate(CompanyFormData formData) throws ProcessingException {
    // nop
    return formData;
  }

  @Override
  public CompanyFormData create(CompanyFormData formData) throws ProcessingException {
    formData.setCompanyNr(SQL.getSequenceNextval("GLOBAL_SEQ"));
    SQL.insert("" +
        "INSERT INTO COMPANY " +
        "           (COMPANY_NR, " +
        "            NAME, " +
        "            SYMBOL) " +
        "VALUES     (:companyNr, " +
        "            :name, " +
        "            :symbol)"
        , formData);

    return formData;
  }

  @Override
  public CompanyFormData load(CompanyFormData formData) throws ProcessingException {
    SQL.selectInto("" +
        "SELECT NAME, " +
        "       SYMBOL " +
        "FROM   COMPANY " +
        "WHERE  COMPANY_NR = :companyNr " +
        "INTO   :name, " +
        "       :symbol "
        , formData);

    // get stock qutoes from webservice
    StockQuoteServiceSoapWebServiceClient service = SERVICES.getService(StockQuoteServiceSoapWebServiceClient.class);
    // get port type to access webservice
    StockQuoteServiceSoap portType = service.getPortType();

    // get quote for the given company
    StockQuote stockQuote = portType.getStockQuote(formData.getSymbol().getValue());
    // load quotes into form data to be transferred to client
    formData.getSymbol().setValue(stockQuote.getStockTickerSymbol());
    formData.getTradeTime().setValue(parseDateTime(stockQuote.getDate(), stockQuote.getTradeTime()));
    formData.getValueLast().setValue(parseDouble(stockQuote.getLastTrade()));
    formData.getValueOpen().setValue(parseDouble(stockQuote.getOpen()));
    formData.getValueLow().setValue(parseDouble(stockQuote.getDayLow()));
    formData.getValueHigh().setValue(parseDouble(stockQuote.getDayHigh()));
    formData.getVolume().setValue(parseLong(stockQuote.getVolume()));
    formData.getChange().setValue(parseDouble(stockQuote.getChangePercentage()));

    return formData;
  }

  @Override
  public CompanyFormData store(CompanyFormData formData) throws ProcessingException {
    SQL.update("" +
        "UPDATE   COMPANY " +
        "SET      NAME = :name, " +
        "         SYMBOL = :symbol " +
        "WHERE    COMPANY_NR = :companyNr"
        , formData);

    return formData;
  }

  private Date parseDateTime(String date, String time) {
    try {
      if (date != null && !date.equals("N/A") && time != null && !time.equals("N/A")) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");
        return format.parse(date + " " + time);
      }
      else if (date != null && !date.equals("N/A")) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.parse(date);
      }
      else if (time != null && !time.equals("N/A")) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mmaa");
        return format.parse(time);
      }
    }
    catch (ParseException e) {
      ScoutLogManager.getLogger(CompanyProcessService.class).error("failed to parse date/time '" + date + " " + time + "'", e);
    }
    return null;
  }

  private Double parseDouble(String number) {
    if (number != null && number.equals("N/A")) {
      return null;
    }
    try {
      return Double.parseDouble(number);
    }
    catch (Exception e) {
      ScoutLogManager.getLogger(CompanyProcessService.class).error("failed to parse double value '" + number + "'", e);
    }
    return null;
  }

  private Long parseLong(String number) {
    if (number != null && number.equals("N/A")) {
      return null;
    }
    try {
      return Long.parseLong(number);
    }
    catch (Exception e) {
      ScoutLogManager.getLogger(CompanyProcessService.class).error("failed to parse long value '" + number + "'", e);
    }
    return null;
  }
}
