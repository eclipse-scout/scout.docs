/*
 * Copyright (c) 2023 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.IDisplayParent;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.ui.forms.FormForm.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.CssClassField;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.IconField;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.NotificationCountField;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.SaveNeededVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.ShowInfoStatusField;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.SubtitleField;
import org.eclipse.scout.widgets.client.ui.forms.FormOptionsForm.MainBox.TitleField;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * @author Andreas Hoegger
 */
@ClassId("98048055-65f4-4500-b751-e3fcad4d1baa")
public class FormOptionsForm extends AbstractForm {

  private static final IStatus INFO_STATUS = new Status(TEXTS.get("SampleInfoStatus"), IStatus.INFO).withIconId(Icons.Info);

  private final IForm m_configurableForm;

  /**
   *
   */
  public FormOptionsForm(IForm configurableForm) {
    m_configurableForm = configurableForm;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("FormOptions");
  }

  @Override
  protected IDisplayParent getConfiguredDisplayParent() {
    return getDesktop();
  }

  @Override
  protected int getConfiguredModalityHint() {
    return MODALITY_HINT_MODELESS;
  }

  public IForm getConfigurableForm() {
    return m_configurableForm;
  }

  public TitleField getTitleField() {
    return getFieldByClass(TitleField.class);
  }

  public IconField getIconField() {
    return getFieldByClass(IconField.class);
  }

  public SubtitleField getSubTitleField() {
    return getFieldByClass(SubtitleField.class);
  }

  public NotificationCountField getNotificationCountField() {
    return getFieldByClass(NotificationCountField.class);
  }

  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public SaveNeededVisibleField getSaveNeededVisibleField() {
    return getFieldByClass(SaveNeededVisibleField.class);
  }

  public ShowInfoStatusField getShowInfoStatusField() {
    return getFieldByClass(ShowInfoStatusField.class);
  }

  public CssClassField getCssClassField() {
    return getFieldByClass(CssClassField.class);
  }

  public MainBox.MaximizedField getMaximizedField() {
    return getFieldByClass(MainBox.MaximizedField.class);
  }

  @Order(1000)
  @ClassId("95ba1502-3931-4a39-b3e2-f7b6c6d845d3")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected String getConfiguredBorderDecoration() {
      return BORDER_DECORATION_EMPTY;
    }

    @Order(0)
    @ClassId("95b3d616-11e8-4db6-8649-2ac768a834a7")
    public class IconField extends AbstractSmartField<String> {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Icon");
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().getIconId());
      }

      @Override
      protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
        return IconIdLookupCall.class;
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setIconId(getValue());
      }
    }

    @Order(500)
    @ClassId("ca12f0e6-4d6b-4cac-9543-e687f6c5dbde")
    public class CssClassField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("CssClass");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().getCssClass());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setCssClass(getValue());
      }
    }

    @Order(1000)
    @ClassId("b41819c4-a682-4d72-8dd3-07d6a5765d95")
    public class TitleField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Title");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().getTitle());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setTitle(getValue());
      }
    }

    @Order(2000)
    @ClassId("55d26edf-2671-4bf7-859b-769d48d5c0c4")
    public class SubtitleField extends AbstractStringField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Subtitle");
      }

      @Override
      protected int getConfiguredMaxLength() {
        return 128;
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().getSubTitle());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setSubTitle(getValue());
      }
    }

    @Order(2500)
    @ClassId("eab83f3f-6d63-4416-b976-c58e52963ff9")
    public class NotificationCountField extends AbstractIntegerField {

      @Override
      protected String getConfiguredLabel() {
        return "Notification count";
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().getNotificationCount());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setNotificationCount(getValue());
      }
    }

    @Order(3000)
    @ClassId("77d914ff-e380-42ff-9159-ccdac0ea4e7b")
    public class ClosableField extends AbstractBooleanField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Closable");
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().isClosable());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setClosable(getValue());
      }
    }

    @Order(4000)
    @ClassId("759caced-dc61-4623-bc91-7362f4e659fa")
    public class SaveNeededVisibleField extends AbstractBooleanField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SaveNeededVisible");
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().isSaveNeededVisible());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setSaveNeededVisible(getValue());
      }
    }

    @Order(5000)
    @ClassId("3b03139a-36b3-4b2b-bca1-ee85b2479ac7")
    public class ShowInfoStatusField extends AbstractBooleanField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShowInfoStatus");
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().getStatus() != null && getConfigurableForm().getStatus().containsStatus(INFO_STATUS));
      }

      @Override
      protected void execChangedValue() {
        if (getValue()) {
          getConfigurableForm().addStatus(INFO_STATUS);
        }
        else {
          getConfigurableForm().removeStatus(INFO_STATUS);
        }
      }
    }

    @Order(5500)
    @ClassId("91bb003a-f4f3-4de5-aa0e-dce60f93015e")
    public class MaximizedField extends AbstractBooleanField {
      @Override
      protected String getConfiguredLabel() {
        return "Maximized";
      }

      @Override
      protected void execInitField() {
        setValue(getConfigurableForm().isMaximized());
      }

      @Override
      protected void execChangedValue() {
        getConfigurableForm().setMaximized(getValue());
      }
    }

    @Order(6000)
    @ClassId("0422a472-935c-4a90-8599-c9b3cab12dfe")
    public class CloseButton extends AbstractCloseButton {
    }

  }
}
