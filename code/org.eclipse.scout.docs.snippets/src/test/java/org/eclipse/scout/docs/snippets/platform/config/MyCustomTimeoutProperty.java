/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.platform.config;

//tag::ConfigProperties[]
import org.eclipse.scout.rt.platform.config.AbstractLongConfigProperty;

/**
 * Property of data type {@link Long} with key 'my.custom.timeout' and default value '3600L'.
 */
public class MyCustomTimeoutProperty extends AbstractLongConfigProperty {

  @Override
  public String getKey() {
    return "my.custom.timeout"; //<1>
  }

  @Override
  public String description() {
    return "Description of the custom timeout property. The default value is 3600.";
  }

  @Override
  public Long getDefaultValue() {
    return 3600L; //<2>
  }
}
//end::ConfigProperties[]
