package org.eclipse.scout.widgets.client.deeplink;

import java.util.regex.Matcher;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.deeplink.AbstractDeepLinkHandler;
import org.eclipse.scout.rt.client.deeplink.DeepLinkException;
import org.eclipse.scout.rt.client.deeplink.DeepLinkUriBuilder;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.Order;

/**
 * Deep-link handler that allows to open a form in bench-mode.
 * <p>
 * This handler sets the desktop in bench mode ({@link IDesktop#DISPLAY_STYLE_BENCH})
 * <ul>
 * <li>Format: <code>form-[formClass]</code>, formClass is the fully qualified class name</li>
 * <li>Example: <code>form-org.eclipse.scout.widgets.client.ui.forms.ButtonForm</code></li>
 * </ul>
 */
@Order(1000)
public class FormDeepLinkHandler extends AbstractDeepLinkHandler {

  private static final String HANDLER_NAME = "form";

  public FormDeepLinkHandler() {
    super(defaultPattern(HANDLER_NAME, ".+"));
  }

  @Override
  public String getName() {
    return HANDLER_NAME;
  }

  /**
   * Creates a deep-link-uri for a given form
   */
  public String createUriForForm(Class<? extends IForm> formClass) {
    return DeepLinkUriBuilder.createRelative()
        .parameterPath(toDeepLinkPath(formClass.getName()))
        .parameter("forceNewClientSession", "true")
        .createURI()
        .toString();
  }

  @Override
  protected void handleImpl(Matcher matcher) throws DeepLinkException {
    String formName = matcher.group(1);

    try {
      Class<?> formClass = Class.forName(formName);
      if (formClass != null && IForm.class.isAssignableFrom(formClass)) {
        IDesktop.CURRENT.get().setDisplayStyle(IDesktop.DISPLAY_STYLE_BENCH);
        IForm form = (IForm) formClass.newInstance();
        form.setTitle(null); // Removing title and subtitle prevents the form from being displayed as a tab.
        form.setSubTitle(null);
        form.setDisplayHint(AbstractForm.DISPLAY_HINT_VIEW);
        form.addFormListener(e -> {
          if (e.getType() == FormEvent.TYPE_CLOSED) {
            IClientSession.CURRENT.get().stop(); // Stopping the client session has also the effect that the popup-window will be closed.
          }
        });
        form.start();
      }
    }
    catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
      throw new DeepLinkException("could not create form: " + formName);
    }
  }
}
