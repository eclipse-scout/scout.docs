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
package org.eclipsescout.demo.minicrm.client.ui.desktop.form;

import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm.MainBox.CancelButton;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm.MainBox.CompanyRatingField;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm.MainBox.CompanyTypeGroup;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm.MainBox.NameField;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm.MainBox.OkButton;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm.MainBox.ShortNameField;
import org.eclipsescout.demo.minicrm.shared.services.code.CompanyRatingCodeType;
import org.eclipsescout.demo.minicrm.shared.services.code.CompanyTypeCodeType;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.CompanyFormData;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.ICompanyService;
import org.eclipsescout.demo.minicrm.shared.ui.desktop.form.UpdateCompanyPermission;

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

  public CompanyRatingField getCompanyRatingField() {
    return getFieldByClass(CompanyRatingField.class);
  }

  public CompanyTypeGroup getCompanyTypeGroup() {
    return getFieldByClass(CompanyTypeGroup.class);
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

  public ShortNameField getShortNameField() {
    return getFieldByClass(ShortNameField.class);
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
    public class ShortNameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShortName");
      }
    }

    @Order(30.0)
    public class CompanyTypeGroup extends AbstractRadioButtonGroup<Long> {

      @Override
      protected Class<? extends ICodeType> getConfiguredCodeType() {
        return CompanyTypeCodeType.class;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CompanyType");
      }
    }

    @Order(40.0)
    public class CompanyRatingField extends AbstractSmartField<Long> {

      @Override
      protected Class<? extends ICodeType<?>> getConfiguredCodeType() {
        return CompanyRatingCodeType.class;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CompanyRating");
      }

      @Override
      protected Class<? extends IValueField> getConfiguredMasterField() {
        return CompanyTypeGroup.class;
      }

      @Override
      protected boolean getConfiguredMasterRequired() {
        return true;
      }

      @Override
      protected boolean getConfiguredVisible() {
        return false;
      }

      @Override
      protected void execChangedMasterValue(Object newMasterValue) throws ProcessingException {

        if (CompareUtility.equals(getCompanyTypeGroup().getValue(), CompanyTypeCodeType.CustomerCode.ID)) {
          setEnabled(true);
          setVisible(true);
        }
        else {
          setEnabled(false);
          setVisible(false);
          setValue(null);
        }
      }
    }

    @Order(50.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(60.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateCompanyPermission());
    }

    @Override
    public void execStore() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      ICompanyService service = SERVICES.getService(ICompanyService.class);
      CompanyFormData formData = new CompanyFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }
}
