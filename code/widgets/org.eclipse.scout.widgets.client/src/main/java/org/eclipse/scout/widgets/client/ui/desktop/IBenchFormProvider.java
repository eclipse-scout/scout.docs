/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop;

import org.eclipse.scout.rt.client.ui.form.IForm;

/**
 * Provides a form instance which is displayed when Desktop is in bench-only mode.
 */
@FunctionalInterface
public interface IBenchFormProvider {

  IForm provideBenchForm();
}
