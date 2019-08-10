/*
 * Copyright (c) 2018 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.scout.widgets.client.deeplink.FormPageDeepLinkHandler;

/**
 * This annotation can be used to define an alias for a widget name used in the {@link FormPageDeepLinkHandler}. By
 * default the DL handler uses the simple class name as widget name, but to avoid naming conflicts with classes with the
 * same name you can use this annotation to provide an alternative, unique name.
 *
 * @see FormPageDeepLinkHandler
 * @see IPageForm
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface WidgetNameAlias {
  String value();
}
