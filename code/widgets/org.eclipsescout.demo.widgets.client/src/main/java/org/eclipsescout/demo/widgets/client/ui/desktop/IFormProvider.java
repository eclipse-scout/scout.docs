/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.ui.desktop;

import org.eclipse.scout.rt.client.ui.form.IForm;

/**
 * Provides a form instance which is displayed when Desktop is in bench-only mode.
 */
// FIXME AWE Rename this interface, and probably also the method. "Form provider" could be anything that provides a form (for any purpose), however this interface seems to be tightly linked to desktop "BENCH" mode.
public interface IFormProvider {

  IForm provideForm();

}
