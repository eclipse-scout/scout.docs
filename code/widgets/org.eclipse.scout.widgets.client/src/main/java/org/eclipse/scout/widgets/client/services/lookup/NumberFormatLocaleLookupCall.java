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
package org.eclipse.scout.widgets.client.services.lookup;

import java.util.Locale;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.util.NumberFormatProvider;

public class NumberFormatLocaleLookupCall extends AbstractLocaleLookupCall {

  private static final long serialVersionUID = -2057181577149853634L;

  @Override
  protected Locale[] availableLocales() {
    return BEANS.get(NumberFormatProvider.class).getAvailableLocales();
  }
}
