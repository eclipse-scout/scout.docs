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
package org.eclipse.scout.tutorial.jaxws.server.services.ws.consumer;

import java.util.List;

import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.eclipse.scout.jaxws.annotation.ScoutWebServiceClient;
import org.eclipse.scout.jaxws.service.AbstractWebServiceClient;
import org.eclipse.scout.service.IService;
import org.eclipse.scout.tutorial.jaxws.server.services.ws.handler.DatabaseLogHandler;

import com.nexus6studio.services.StockQuoteService;
import com.nexus6studio.services.StockQuoteServiceSoap;

@ScoutWebServiceClient
public class StockQuoteServiceSoapWebServiceClient extends AbstractWebServiceClient<StockQuoteService, StockQuoteServiceSoap> implements IService {

  @Override
  protected String getConfiguredUrl() {
    return "http://services.nexus6studio.com/StockQuoteService.asmx";
  }

  @Override
  protected void execInstallHandlers(List<SOAPHandler<SOAPMessageContext>> handlers) {
    handlers.add(new DatabaseLogHandler());
  }
}
