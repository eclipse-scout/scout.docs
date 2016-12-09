package org.eclipse.scout.widgets.ui.html;

import static org.eclipse.scout.rt.ui.html.selenium.util.SeleniumUtil.byModelClass;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.ui.html.selenium.junit.AbstractSeleniumTest;
import org.eclipse.scout.widgets.client.ui.desktop.Desktop.AdvancedWidgetsOutlineViewButton;
import org.eclipse.scout.widgets.client.ui.desktop.Desktop.SimpleWidgetsOutlineViewButton;
import org.openqa.selenium.By;

/**
 * Utility class with methods to simplify selenium tests in OSS Eclipse Scout widgets application.
 */
public final class WidgetsSeleniumUtil {

  /**
   * Go to a form in the 'Simple Widget' outline.
   */
  public static void goToSimpleWidgetsForm(AbstractSeleniumTest test, String formTitle) {
    goToWidgetsForm(test, SimpleWidgetsOutlineViewButton.class, formTitle);
  }

  /**
   * Go to a form in the 'Advanced Widget' outline.
   */
  public static void goToAdvancedWidgetsForm(AbstractSeleniumTest test, String formTitle) {
    goToWidgetsForm(test, AdvancedWidgetsOutlineViewButton.class, formTitle);
  }

  private static void goToWidgetsForm(AbstractSeleniumTest test, Class<? extends AbstractOutlineViewButton> viewButtonClass, String formTitle) {
    test.waitUntilElementClickable(By.className("view-button-tab")).click();
    test.waitUntilElementClickable(byModelClass(viewButtonClass)).click();
    test.waitUntilElementClickable(By.xpath("//div[contains(@class, 'tree-node')]/span[text() = '" + formTitle + "']")).click();
  }

}
