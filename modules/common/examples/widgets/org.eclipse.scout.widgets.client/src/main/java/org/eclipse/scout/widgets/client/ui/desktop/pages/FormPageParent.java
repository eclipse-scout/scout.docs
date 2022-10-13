package org.eclipse.scout.widgets.client.ui.desktop.pages;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.scout.widgets.client.deeplink.FormPageDeepLinkHandler;

/**
 * Marker for pages that may contain {@link IFormPage}s as children. Useful when resolving deep links, see
 * {@link FormPageDeepLinkHandler}.
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface FormPageParent {
}
