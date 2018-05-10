package org.eclipse.scout.widgets.client.deeplink;

import java.util.regex.Matcher;

import org.eclipse.scout.rt.client.deeplink.AbstractDeepLinkHandler;
import org.eclipse.scout.rt.client.deeplink.DeepLinkException;
import org.eclipse.scout.rt.client.deeplink.DeepLinkUriBuilder;
import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.BrowserHistoryEntry;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPageParent;
import org.eclipse.scout.widgets.client.ui.desktop.pages.IFormPage;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

/**
 * Deep-link handler allows to navigate to a specific outline and form within the widgets application.
 * <ul>
 * <li>Format: <code>widget-[formName]</code>, Form name is a string created from the class name of the widgets form
 * without the 'Form' suffix.</li>
 * <li>Example: <code>widget-stringfield</code></li>
 * </ul>
 */
@Order(1100)
public class WidgetsDeepLinkHandler extends AbstractDeepLinkHandler {

  private static final String HANDLER_NAME = "widget";

  public WidgetsDeepLinkHandler() {
    super(defaultPattern(HANDLER_NAME, "[A-Za-z0-9_]+"));
  }

  @Override
  public String getName() {
    return HANDLER_NAME;
  }

  protected static String toWidgetName(Class<?> formClass) {
    String widgetName = formClass.getSimpleName();
    widgetName = widgetName.replaceAll("Form$", "");
    return widgetName.toLowerCase();
  }

  public BrowserHistoryEntry createBrowserHistoryEntry(IFormPage formPage) {
    String widgetName = toWidgetName(formPage.getFormType());
    return DeepLinkUriBuilder.createRelative()
        .info(formPage.getCell().getText())
        .parameterPath(toDeepLinkPath(widgetName))
        .createBrowserHistoryEntry();
  }

  @Override
  protected void handleImpl(Matcher matcher) throws DeepLinkException {
    String widgetName = matcher.group(1);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    IOutline outlineToActivate = null;
    IPage pageToActivate = null;
    for (IOutline outline : desktop.getAvailableOutlines()) {
      pageToActivate = findPageToActivateRec(widgetName, outline.getRootNode());
      if (pageToActivate != null) {
        outlineToActivate = outline;
        break;
      }
    }
    if (outlineToActivate == null) {
      throw new DeepLinkException("outline could not be resolved for widget: " + widgetName);
    }
    if (desktop.getOutline() != outlineToActivate) {
      desktop.activateOutline(outlineToActivate);
    }
    IPage p = pageToActivate;
    while (p != null) {
      p.setExpanded(true);
      p = p.getParentPage();
    }
    outlineToActivate.selectNode(pageToActivate);
  }

  protected IPage findPageToActivateRec(String widgetName, ITreeNode parentNode) {
    parentNode.ensureChildrenLoaded();
    for (ITreeNode childNode : parentNode.getChildNodes()) {
      if (childNode.isVisible() && childNode instanceof IFormPage) {
        IFormPage formPage = (IFormPage) childNode;
        Class<? extends IPageForm> formType = formPage.getFormType();
        if (formType == null) {
          continue;
        }
        String tmpWidgetName = toWidgetName(formType);
        if (widgetName.equals(tmpWidgetName)) {
          return (IPage) childNode;
        }
      }
      if (childNode.isVisible() && childNode.getClass().getAnnotation(FormPageParent.class) != null) {
        IPage recResult = findPageToActivateRec(widgetName, childNode);
        if (recResult != null) {
          return recResult;
        }
      }
    }
    return null;
  }
}
