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
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * Helper class to test image alignment.
 *
 * @since 9.0
 */
public class AbstractAlignedImageField extends AbstractImageField {

  private final int m_verticalAlignment;
  private final int m_horizontalAlignment;

  public AbstractAlignedImageField(int verticalAlignment, int horizontalAlignment) {
    super(false);
    m_verticalAlignment = verticalAlignment;
    m_horizontalAlignment = horizontalAlignment;
    callInitializer();
  }

  @Override
  protected int getConfiguredVerticalAlignment() {
    return m_verticalAlignment;
  }

  @Override
  protected int getConfiguredHorizontalAlignment() {
    return m_horizontalAlignment;
  }

  @Override
  protected String getConfiguredTooltipText() {
    return "verticalAlignment: " + m_verticalAlignment + " / horizontalAlignment: " + m_horizontalAlignment;
  }

  @Override
  protected String getConfiguredImageId() {
    return Icons.EclipseScout;
  }

  @Override
  protected String getConfiguredCssClass() {
    return "highlight";
  }

  @Override
  protected boolean getConfiguredLabelVisible() {
    return false;
  }

  @Override
  protected boolean getConfiguredStatusVisible() {
    return false;
  }
}
