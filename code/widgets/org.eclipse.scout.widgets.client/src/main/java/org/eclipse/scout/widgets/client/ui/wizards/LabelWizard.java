/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.wizards;

import org.eclipse.scout.rt.client.ui.wizard.AbstractWizard;
import org.eclipse.scout.rt.client.ui.wizard.AbstractWizardStep;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;

@ClassId("d9ed6351-416f-4029-8390-744a54069676")
public class LabelWizard extends AbstractWizard {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("LabelWizard");
  }

  @Override
  protected void execRefreshButtonPolicy() {
    super.execRefreshButtonPolicy();
    getContainerForm().getWizardFinishButton().setEnabled(false);
  }

  @Order(10)
  @ClassId("4915875f-bca0-4b6b-bc73-0ec2870d1576")
  public class FontStep extends AbstractWizardStep<LabelWizardFontForm> {

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Font");
    }

    @Override
    protected String getConfiguredSubTitle() {
      return "Step 1";
    }

    @Override
    protected String getConfiguredTooltipText() {
      return "Choose the desired font";
    }

    @Override
    protected void execActivate(int stepKind) {
      LabelWizardFontForm form = getForm();
      if (form == null) {
        form = new LabelWizardFontForm();
        form.startWizardStep(this, LabelWizardFontForm.WizardHandler.class);
        setForm(form);
      }
      getWizard().setWizardForm(form);
    }
  }

  public FontStep getFontStep() {
    return getStep(FontStep.class);
  }

  @Order(20)
  @ClassId("0a6e51f6-f99d-4edd-8f3d-4213293ffef3")
  public class SizeStep extends AbstractWizardStep<LabelWizardSizeForm> {

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Size");
    }

    @Override
    protected String getConfiguredSubTitle() {
      return "Step 2";
    }

    @Override
    protected String getConfiguredTooltipText() {
      return "Choose the desired size";
    }

    @Override
    protected void execActivate(int stepKind) {
      LabelWizardSizeForm form = getForm();
      if (form == null) {
        form = new LabelWizardSizeForm();
        form.startWizardStep(this, LabelWizardSizeForm.WizardHandler.class);
        setForm(form);
      }
      getWizard().setWizardForm(form);
    }
  }

  public SizeStep getSizeStep() {
    return getStep(SizeStep.class);
  }

  @Order(30)
  @ClassId("bb3beed9-8aa8-4234-9ad4-9300d56f717e")
  public class ForegroundColorStep extends AbstractWizardStep<LabelWizardForegroundColorForm> {

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("ForegroundColor");
    }

    @Override
    protected String getConfiguredSubTitle() {
      return "Step 3";
    }

    @Override
    protected void execActivate(int stepKind) {
      LabelWizardForegroundColorForm form = getForm();
      if (form == null) {
        form = new LabelWizardForegroundColorForm();
        form.startWizardStep(this, LabelWizardForegroundColorForm.WizardHandler.class);
        setForm(form);
      }
      getWizard().setWizardForm(form);
    }
  }

  public ForegroundColorStep getForegroundColorStep() {
    return getStep(ForegroundColorStep.class);
  }

  @Order(40)
  @ClassId("4839e636-37d9-4617-9bd2-cd68112c0858")
  public class LabelStep extends AbstractWizardStep<LabelWizardLabelForm> {

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Label");
    }

    @Override
    protected String getConfiguredSubTitle() {
      return "Step 4";
    }

    @Override
    protected void execActivate(int stepKind) {
      LabelWizardLabelForm form = new LabelWizardLabelForm();
      String font = ObjectUtility.nvl(getFontStep().getForm().getFontField().getValue(), "Serif");
      Integer fontSize = ObjectUtility.nvl(getSizeStep().getForm().getSizeField().getValue(), 14);
      String foregroundColor = ObjectUtility.nvl(getForegroundColorStep().getForm().getForegroundColorField().getValue(), "000000");
      form.getLoremField().setFont(new FontSpec(font, 0, fontSize));
      form.getLoremField().setForegroundColor(foregroundColor);
      form.startWizardStep(this, LabelWizardLabelForm.WizardHandler.class);
      setForm(form);
      getWizard().setWizardForm(form);
    }
  }

  public LabelStep getLabelStep() {
    return getStep(LabelStep.class);
  }
}
