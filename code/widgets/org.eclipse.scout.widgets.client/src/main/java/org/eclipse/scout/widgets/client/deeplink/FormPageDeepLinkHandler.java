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
import org.eclipse.scout.rt.platform.holders.Holder;
import org.eclipse.scout.rt.platform.util.visitor.DepthFirstTreeVisitor;
import org.eclipse.scout.rt.platform.util.visitor.IDepthFirstTreeVisitor;
import org.eclipse.scout.rt.platform.util.visitor.TreeVisitResult;
import org.eclipse.scout.widgets.client.ui.desktop.pages.FormPageParent;
import org.eclipse.scout.widgets.client.ui.desktop.pages.IFormPage;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.client.ui.forms.WidgetNameAlias;

/**
 * Deep-link handler allows to navigate to a specific outline and form within the widgets application.
 * <ul>
 * <li>Format: <code>widget-[formName]</code>, Form name is a string created from the class name of the widgets form
 * without the 'Form' suffix.</li>
 * <li>Example: <code>widget-stringfield</code></li>
 * </ul>
 */
@Order(1100)
public class FormPageDeepLinkHandler extends AbstractDeepLinkHandler {

  private static final String HANDLER_NAME = "widget";

  public FormPageDeepLinkHandler() {
    super(defaultPattern(HANDLER_NAME, "[A-Za-z0-9_]+"));
  }

  @Override
  public String getName() {
    return HANDLER_NAME;
  }

  protected static String toWidgetName(Class<?> formClass) {
    WidgetNameAlias annotation = formClass.getAnnotation(WidgetNameAlias.class);
    String widgetName;
    if (annotation == null) {
      // default: derive widget name from class name
      widgetName = formClass.getSimpleName().replaceAll("Form$", "");
    }
    else {
      // if annotation is present: use widget name from annotation value
      widgetName = annotation.value();
    }
    return widgetName.toLowerCase();
  }

  public BrowserHistoryEntry createBrowserHistoryEntry(IFormPage formPage) {
    return createUriForPage(formPage).createBrowserHistoryEntry();
  }

  public DeepLinkUriBuilder createUriForPage(IFormPage formPage) {
    String widgetName = toWidgetName(formPage.getFormType());
    return DeepLinkUriBuilder.createRelative()
        .info(formPage.getCell().getText())
        .parameterPath(toDeepLinkPath(widgetName));
  }

  @Override
  protected void handleImpl(Matcher matcher) throws DeepLinkException {
    String widgetName = matcher.group(1);
    IDesktop desktop = ClientSessionProvider.currentSession().getDesktop();
    Holder<IOutline> outlineToActivateHolder = new Holder<>(IOutline.class);
    Holder<IPage> pageToActivateHolder = new Holder<>(IPage.class);
    for (IOutline outline : desktop.getAvailableOutlines()) {
      ITreeNode rootNode = outline.getRootNode();

      IDepthFirstTreeVisitor<ITreeNode> v = new DepthFirstTreeVisitor<ITreeNode>() {
        @Override
        public TreeVisitResult preVisit(ITreeNode element, int level, int index) {
          if (element.isVisible() && element.getClass().getAnnotation(FormPageParent.class) != null) {
            element.ensureChildrenLoaded();
          }
          if (element.isVisible() && element instanceof IFormPage) {
            IFormPage formPage = (IFormPage) element;
            Class<? extends IPageForm> formType = formPage.getFormType();
            if (formType == null) {
              return TreeVisitResult.CONTINUE;
            }
            String tmpWidgetName = toWidgetName(formType);
            if (widgetName.equals(tmpWidgetName)) {
              outlineToActivateHolder.setValue(outline);
              pageToActivateHolder.setValue((IPage) element);
              return TreeVisitResult.TERMINATE;
            }
          }
          return TreeVisitResult.CONTINUE;
        }
      };
      outline.visitNode(rootNode, v);
    }
    if (outlineToActivateHolder.getValue() == null) {
      throw new DeepLinkException("outline could not be resolved for widget: " + widgetName);
    }
    if (desktop.getOutline() != outlineToActivateHolder.getValue()) {
      desktop.activateOutline(outlineToActivateHolder.getValue());
    }
    IPage p = pageToActivateHolder.getValue();
    while (p != null) {
      p.setExpanded(true);
      p = p.getParentPage();
    }
    outlineToActivateHolder.getValue().selectNode(pageToActivateHolder.getValue());
  }
}
