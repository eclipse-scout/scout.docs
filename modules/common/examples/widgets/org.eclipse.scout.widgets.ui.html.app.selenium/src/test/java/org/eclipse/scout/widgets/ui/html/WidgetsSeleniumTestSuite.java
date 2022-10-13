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
