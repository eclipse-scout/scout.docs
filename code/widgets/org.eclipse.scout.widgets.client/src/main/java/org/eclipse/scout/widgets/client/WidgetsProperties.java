/*
 * Copyright (c) 2020 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;

public class WidgetsProperties {

  public static class ReadOnlyProperty extends AbstractBooleanConfigProperty {

    @Override
    public String getKey() {
      return "widgets.readOnly";
    }

    @Override
    public String description() {
      return "Global flag to activate a read-only mode when widgets application is deployed as public available demo application";
    }

    @Override
    public Boolean getDefaultValue() {
      return false;
    }
  }

}
