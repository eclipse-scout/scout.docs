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
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.CancelButton;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.DateField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.OkButton;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.OperationField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.PortField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.ServiceField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.SoapMessageBox;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.SoapMessageBox.RequestBox;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.SoapMessageBox.RequestBox.RequestField;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.SoapMessageBox.ResponseBox;
import org.eclipse.scout.tutorial.jaxws.client.ui.forms.WSLogForm.MainBox.SoapMessageBox.ResponseBox.ResponseField;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.IWSLogProcessService;
import org.eclipse.scout.tutorial.jaxws.shared.services.process.WSLogFormData;

@FormData(value = WSLogFormData.class, sdkCommand = SdkCommand.CREATE)
public class WSLogForm extends AbstractForm {

  private Long wSLogNr;

  public WSLogForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("WSLog");
  }

  @FormData
  public Long getWSLogNr() {
    return wSLogNr;
  }

  @FormData
  public void setWSLogNr(Long wSLogNr) {
    this.wSLogNr = wSLogNr;
  }

  public void startModify() throws ProcessingException {
    startInternal(new ModifyHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public DateField getDateField() {
    return getFieldByClass(DateField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public OperationField getOperationField() {
    return getFieldByClass(OperationField.class);
  }

  public PortField getPortField() {
    return getFieldByClass(PortField.class);
  }

  public RequestBox getRequestBox() {
    return getFieldByClass(RequestBox.class);
  }

  public RequestField getRequestField() {
    return getFieldByClass(RequestField.class);
  }

  public ResponseBox getResponseBox() {
    return getFieldByClass(ResponseBox.class);
  }

  public ResponseField getResponseField() {
    return getFieldByClass(ResponseField.class);
  }

  public ServiceField getServiceField() {
    return getFieldByClass(ServiceField.class);
  }

  public SoapMessageBox getSoapMessageBox() {
    return getFieldByClass(SoapMessageBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class DateField extends AbstractDateField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Date");
      }
    }

    @Order(20.0)
    public class ServiceField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Service");
      }
    }

    @Order(30.0)
    public class PortField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Port");
      }
    }

    @Order(40.0)
    public class OperationField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Operation");
      }
    }

    @Order(50.0)
    public class SoapMessageBox extends AbstractTabBox {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Order(10.0)
      public class RequestBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Request");
        }

        @Order(10.0)
        public class RequestField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected int getConfiguredGridW() {
            return 0;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredMaxLength() {
            return Integer.MAX_VALUE;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }
        }
      }

      @Order(20.0)
      public class ResponseBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Response");
        }

        @Order(10.0)
        public class ResponseField extends AbstractStringField {

          @Override
          protected int getConfiguredGridH() {
            return 5;
          }

          @Override
          protected int getConfiguredGridW() {
            return 0;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected int getConfiguredMaxLength() {
            return Integer.MAX_VALUE;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected boolean getConfiguredWrapText() {
            return true;
          }
        }
      }
    }

    @Order(60.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(70.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IWSLogProcessService service = SERVICES.getService(IWSLogProcessService.class);
      WSLogFormData formData = new WSLogFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);

      // disable whole form 
      setEnabledGranted(false);
    }
  }
}
