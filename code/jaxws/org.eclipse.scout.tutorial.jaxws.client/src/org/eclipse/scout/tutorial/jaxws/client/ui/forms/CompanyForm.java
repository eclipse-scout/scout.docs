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
package org.eclipse.scout.tutorial.jaxws.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.doublefield.AbstractDoubleField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.CancelButton;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.ChangeField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.NameField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.OkButton;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.SymbolField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.TradeTimeField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.ValueHighField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.ValueLastField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.ValueLowField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.ValueOpenField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.CompanyForm.MainBox.VolumeField;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.CompanyFormData;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.ICompanyProcessService;

@FormData(value = CompanyFormData.class, sdkCommand = SdkCommand.CREATE)
public class CompanyForm extends AbstractForm {

  private Long companyNr;

  public CompanyForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Company");
  }

  @FormData
  public Long getCompanyNr() {
    return companyNr;
  }

  @FormData
  public void setCompanyNr(Long companyNr) {
    this.companyNr = companyNr;
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ChangeField getChangeField() {
    return getFieldByClass(ChangeField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public SymbolField getSymbolField() {
    return getFieldByClass(SymbolField.class);
  }

  public TradeTimeField getTradeTimeField() {
    return getFieldByClass(TradeTimeField.class);
  }

  public ValueHighField getValueHighField() {
    return getFieldByClass(ValueHighField.class);
  }

  public ValueLastField getValueLastField() {
    return getFieldByClass(ValueLastField.class);
  }

  public ValueLowField getValueLowField() {
    return getFieldByClass(ValueLowField.class);
  }

  public ValueOpenField getValueOpenField() {
    return getFieldByClass(ValueOpenField.class);
  }

  public VolumeField getVolumeField() {
    return getFieldByClass(VolumeField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class NameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Name");
      }
    }

    @Order(20.0)
    public class SymbolField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Symbol");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 5;
      }
    }

    @Order(30.0)
    public class TradeTimeField extends AbstractDateField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("TradeTime");
      }
    }

    @Order(40.0)
    public class ValueLastField extends AbstractDoubleField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ValueLast");
      }
    }

    @Order(50.0)
    public class ValueOpenField extends AbstractDoubleField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ValueOpen");
      }
    }

    @Order(60.0)
    public class ValueLowField extends AbstractDoubleField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ValueLow");
      }
    }

    @Order(70.0)
    public class ValueHighField extends AbstractDoubleField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ValueHigh");
      }
    }

    @Order(80.0)
    public class VolumeField extends AbstractLongField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Volume");
      }
    }

    @Order(90.0)
    public class ChangeField extends AbstractDoubleField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Change");
      }

      @Override
      protected boolean getConfiguredPercent() {
        return true;
      }
    }

    @Order(100.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(110.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      ICompanyProcessService service = SERVICES.getService(ICompanyProcessService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
