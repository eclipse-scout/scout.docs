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
package org.eclipsescout.demo.minifigcreator.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.FormData.SdkCommand;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagebox.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.NameField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.PartsBox;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.PartsBox.HeadField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.PartsBox.LegsField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.PartsBox.PlaceholderField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.PartsBox.TorsoField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.PreviewField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ContainerBox.SummaryField;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.ExportButton;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm.MainBox.Placeholder2Field;
import org.eclipsescout.demo.minifigcreator.shared.Icons;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.PartUtility;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.HeadLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.LegsLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.TorsoLookupCall;
import org.eclipsescout.demo.minifigcreator.shared.services.process.DesktopFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.FormState;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IDesktopProcessService;

@FormData(value = DesktopFormData.class, sdkCommand = SdkCommand.CREATE)
public class DesktopForm extends AbstractForm {

  private FormState m_state;

  public DesktopForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_CENTER;
  }

  @Override
  protected String getConfiguredIconId() {
    return Icons.EclipseScout;
  }

  @FormData
  public FormState getState() {
    return m_state;
  }

  @FormData
  public void setState(FormState state) {
    m_state = state;
  }

  public void startView() throws ProcessingException {
    startInternal(new ViewHandler());
  }

  public ContainerBox getContainerBox() {
    return getFieldByClass(ContainerBox.class);
  }

  public ExportButton getExportButton() {
    return getFieldByClass(ExportButton.class);
  }

  public HeadField getHeadField() {
    return getFieldByClass(HeadField.class);
  }

  public LegsField getLegsField() {
    return getFieldByClass(LegsField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public PartsBox getPartsBox() {
    return getFieldByClass(PartsBox.class);
  }

  public Placeholder2Field getPlaceholder2Field() {
    return getFieldByClass(Placeholder2Field.class);
  }

  public PlaceholderField getPlaceholderField() {
    return getFieldByClass(PlaceholderField.class);
  }

  public PreviewField getPreviewField() {
    return getFieldByClass(PreviewField.class);
  }

  public TorsoField getTorsoField() {
    return getFieldByClass(TorsoField.class);
  }

  public SummaryField getValueField() {
    return getFieldByClass(SummaryField.class);
  }

  private void updateImage() {
    getPreviewField().setImageId(PartUtility.calculateImageId(
        getHeadField().getValue(),
        getTorsoField().getValue(),
        getLegsField().getValue()
        ));
  }

  private void updateSummary() {
    getValueField().setValue(PartUtility.calculateSummary(
        getNameField().getValue(),
        getHeadField().getValue(),
        getTorsoField().getValue(),
        getLegsField().getValue()
        ));
  }

  public void reloadForm() throws ProcessingException {
    IDesktopProcessService service = SERVICES.getService(IDesktopProcessService.class);
    DesktopFormData formData = new DesktopFormData();
    exportFormData(formData);
    formData = service.load(formData);
    importFormData(formData);
    FormState state = getState();
    if (state != null) {
      getHeadField().setEnabled(state.isHeadEnabled());
      getTorsoField().setEnabled(state.isTorsoEnabled());
      getLegsField().setEnabled(state.isLegsEnabled());
    }
    updateImage();
    updateSummary();
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ContainerBox extends AbstractGroupBox {

      @Order(10.0)
      public class NameField extends AbstractStringField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          updateSummary();
        }
      }

      @Order(20.0)
      public class PartsBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class HeadField extends AbstractSmartField<Part> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Head");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return HeadLookupCall.class;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            updateImage();
            updateSummary();
          }
        }

        @Order(20.0)
        public class TorsoField extends AbstractSmartField<Part> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Torso");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return TorsoLookupCall.class;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            updateImage();
            updateSummary();
          }
        }

        @Order(30.0)
        public class LegsField extends AbstractSmartField<Part> {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Legs");
          }

          @Override
          protected Class<? extends LookupCall> getConfiguredLookupCall() {
            return LegsLookupCall.class;
          }

          @Override
          protected boolean getConfiguredMandatory() {
            return true;
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            updateImage();
            updateSummary();
          }
        }

        @Order(40.0)
        public class PlaceholderField extends AbstractLabelField {

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }
        }
      }

      @Order(30.0)
      public class PreviewField extends AbstractImageField {

        @Override
        protected boolean getConfiguredAutoFit() {
          return true;
        }

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected String getConfiguredImageId() {
          return "Minifig_H00_T00_L00";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Preview");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }
      }

      @Order(40.0)
      public class SummaryField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Summary");
        }
      }
    }

    @Order(100.0)
    public class Placeholder2Field extends AbstractLabelField {

      @Override
      protected int getConfiguredGridW() {
        return 2;
      }
    }

    @Order(110.0)
    public class ExportButton extends AbstractButton {

      @Override
      protected int getConfiguredHorizontalAlignment() {
        return 1;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Export");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        validateForm();

        IDesktopProcessService service = SERVICES.getService(IDesktopProcessService.class);
        DesktopFormData formData = new DesktopFormData();
        exportFormData(formData);
        formData = service.store(formData);
        //Add the export code here.
        resetForm();
        reloadForm();
      }

      private void resetForm() throws ProcessingException {
        DesktopFormData formData = new DesktopFormData();
        formData.getName().setValueSet(true);
        formData.getHead().setValueSet(true);
        formData.getTorso().setValueSet(true);
        formData.getLegs().setValueSet(true);
        importFormData(formData);
      }
    }
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      reloadForm();
    }
  }
}
