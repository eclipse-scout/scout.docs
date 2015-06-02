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
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class ProductLookupCall extends LocalLookupCall<Long> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<ILookupRow<Long>> execCreateLookupRows() throws ProcessingException {
    ArrayList<ILookupRow<Long>> rows = new ArrayList<ILookupRow<Long>>();

    int N = 5;
    rows = new ArrayList<ILookupRow<Long>>();
    for (long i = 1; i < N; i++) {
      rows.add(new LookupRow<Long>(i, "Domain " + i, null, null, null, null, null, true, 0L));
      for (long j = 1; j < N; j++) {
        rows.add(new LookupRow<Long>(i + j * 10, "Category " + i + "-" + j, null, null, null, null, null, true, i));
        for (long k = 1; k < N; k++) {
          rows.add(new LookupRow<Long>(i + j * 10 + k * 100, "Shelf " + i + "-" + j + "-" + k, null, null, null, null, null, true, i + j * 10));
          for (long l = 1; l < N; l++) {
            rows.add(new LookupRow<Long>(i + j * 10 + k * 100 + l * 1000, "Item " + i + "." + j + "." + k + "." + l, null, null, null, null, null, true, i + j * 10 + k * 100));
          }
        }
      }
    }
    return rows;
  }
}
