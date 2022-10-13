package org.eclipse.scout.widgets.ui.html;

import org.eclipse.scout.rt.ui.html.selenium.junit.AbstractSeleniumTest;
import org.eclipse.scout.rt.ui.html.selenium.junit.SeleniumAssert;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.ButtonFieldButton;
import org.eclipse.scout.widgets.client.ui.forms.ButtonForm.MainBox.ConfigurationBox.GetValueField;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

/**
 * Selenium test for ButtonForm.
 */
public class ButtonFormTest extends AbstractSeleniumTest {

  @Before
  public void setUp() {
    WidgetsSeleniumUtil.goToSimpleWidgetsForm(this, "Button");
  }

  @Test
  public void testToggleButton() {
    WebElement toggleButton = waitUntilElementClickable(ButtonFieldButton.class);
    WebElement getValueField = findInputField(GetValueField.class);
    toggleButton.click();
    SeleniumAssert.assertInputFieldValue(this, getValueField, "true");
  }

}
