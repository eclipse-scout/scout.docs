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

//tag::code[]
import org.eclipse.scout.rt.platform.IgnoreBean;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.config.PlatformConfigProperties.ApplicationNameProperty;

//end::code[]
@IgnoreBean
//tag::code[]
@Replace
public class ApplicationNamePropertyRedirection extends ApplicationNameProperty {

  @Override
  public String getKey() {
    return "myproject.applicationName";
  }
}
//end::code[]
