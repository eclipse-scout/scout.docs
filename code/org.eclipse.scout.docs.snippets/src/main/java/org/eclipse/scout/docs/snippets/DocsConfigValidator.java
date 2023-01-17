/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.platform.config.IConfigurationValidator;

public class DocsConfigValidator implements IConfigurationValidator {

  @Override
  public boolean isValid(String key, String value) {
    // the config.properties contains an example entry with this key. It should be ignored in validation.
    return "myproject.applicationName".equals(key);
  }
}
