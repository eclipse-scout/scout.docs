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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.io.IOException;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.svg.client.SVGUtility;
import org.eclipse.scout.svg.client.svgfield.AbstractSvgField;
import org.eclipse.scout.svg.client.svgfield.SvgFieldEvent;
import org.eclipsescout.demo.widgets.client.Activator;
import org.eclipsescout.demo.widgets.client.ui.forms.SVGFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.SVGFieldForm.MainBox.SVGField;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGPointList;
import org.w3c.dom.svg.SVGPolygonElement;

public class SVGFieldForm extends AbstractForm implements IPageForm {

  public SVGFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SVGField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public SVGField getSVGField() {
    return getFieldByClass(SVGField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class SVGField extends AbstractSvgField {

      @Override
      protected int getConfiguredGridH() {
        return 10;
      }

      @Override
      protected int getConfiguredGridW() {
        return 0;
      }

      @Override
      protected void execHyperlink(SvgFieldEvent e) throws ProcessingException {
        //create a copy...
        SVGDocument doc = (SVGDocument) getSvgDocument().cloneNode(true);
        //...and move a corner
        SVGPolygonElement poly = (SVGPolygonElement) doc.getElementById("poly");
        SVGPointList pointList = poly.getPoints();
        SVGPoint p = pointList.getItem(2);
        p.setX(p.getX() + 10);
        setSvgDocument(doc);
      }
    }

    @Order(30.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      try {
        getSVGField().setSvgDocument(SVGUtility.readSVGDocument(Activator.getDefault().getBundle().getResource("/resources/svg/demo.svg").openStream()));
      }
      catch (IOException e) {
        throw new ProcessingException("Can't open SVG-File", e);
      }
    }
  }
}
