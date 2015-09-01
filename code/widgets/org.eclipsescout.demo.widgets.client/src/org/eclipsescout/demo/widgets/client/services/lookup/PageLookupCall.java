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
package org.eclipsescout.demo.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.widgets.client.ui.desktop.pages.PageWithDetailForm;

public class PageLookupCall extends LocalLookupCall<IPageWithTable> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<ILookupRow<IPageWithTable>> execCreateLookupRows() throws ProcessingException {
    List<ILookupRow<IPageWithTable>> rows = new ArrayList<>();
    rows.add(new LookupRow<IPageWithTable>(new PageWithDetailForm(), TEXTS.get("PageWithDetailForm")));
    return rows;
  }
}
