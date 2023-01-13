/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.ui.html.selenium.SeleniumSuiteState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Selenium test suite for OSS Eclipse Scout widgets application.
 *
 * @author awe
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ButtonFormTest.class
})
public class WidgetsSeleniumTestSuite {

  @BeforeClass
  public static void setUpBeforeClass() {
    SeleniumSuiteState.setUpBeforeClass();
  }

  @AfterClass
  public static void tearDownAfterClass() {
    SeleniumSuiteState.tearDownAfterClass();
  }

}
