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
package org.eclipse.scout.tutorial.jaxws.server.services.ws.handler;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.commons.xmlparser.ScoutXmlDocument;
import org.eclipse.scout.jaxws.annotation.ScoutTransaction;
import org.eclipse.scout.jaxws.handler.LogHandler;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;

@ScoutTransaction
public class DatabaseLogHandler extends LogHandler {

  private static final IScoutLogger LOG = ScoutLogManager.getLogger(DatabaseLogHandler.class);

  /**
   * Property to access the primary key of the log entry in the message context.
   */
  public static final String PROP_WS_LOG_NR = UUID.randomUUID().toString();

  @Override
  protected void handleLogMessage(DirectionType directionType, String soapMessage, SOAPMessageContext context) {
    try {
      /*
       * If this handler is invoked to post-process the SOAP-response, the SOAP-request was already logged and
       * the log's primary key put into the message context. In consequence, the log entry is updated with the SOAP response.
       */
      Long wsLogNr = (Long) context.get(PROP_WS_LOG_NR);
      if (wsLogNr == null) {
        // SOAP request
        wsLogNr = SQL.getSequenceNextval("GLOBAL_SEQ");
        context.put(PROP_WS_LOG_NR, wsLogNr);

        SQL.insert("" +
            "INSERT INTO   WS_LOG " +
            "             (WS_LOG_NR, " +
            "              EVT_DATE, " +
            "              SERVICE, " +
            "              PORT, " +
            "              OPERATION, " +
            "              REQUEST) " +
            "VALUES       (:wsLogNr, " +
            "              :evtDate, " +
            "              :service, " +
            "              :port, " +
            "              :operation, " +
            "              :request)"
            , new NVPair("wsLogNr", wsLogNr)
            , new NVPair("service", StringUtility.nvl(context.get(SOAPMessageContext.WSDL_SERVICE), "?"))
            , new NVPair("port", StringUtility.nvl(context.get(SOAPMessageContext.WSDL_PORT), "?"))
            , new NVPair("operation", StringUtility.nvl(context.get(SOAPMessageContext.WSDL_OPERATION), "?"))
            , new NVPair("request", wellformXml(soapMessage))
            , new NVPair("evtDate", new Date())
            );
      }
      else {
        // SOAP response
        SQL.update("" +
            "UPDATE   WS_LOG " +
            "SET      RESPONSE = :response " +
            "WHERE    WS_LOG_NR = :wsLogNr"
            , new NVPair("wsLogNr", wsLogNr)
            , new NVPair("response", wellformXml(soapMessage))
            );
      }
    }
    catch (ProcessingException e) {
      LOG.error("failed to persist webservice-log", e);
    }
  }

  private String wellformXml(String rawXml) {
    try {
      ScoutXmlDocument doc = new ScoutXmlDocument(rawXml);
      doc.setPrettyPrint(true);
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      doc.write(os);
      return new String(os.toByteArray(), doc.getXmlEncoding());
    }
    catch (Exception e) {
      LOG.warn("failed to wellform XML", e);
    }
    return rawXml;
  }
}
