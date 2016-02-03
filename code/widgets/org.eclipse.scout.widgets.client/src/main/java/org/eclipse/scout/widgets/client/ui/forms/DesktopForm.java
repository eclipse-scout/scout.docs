package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.desktop.notification.DesktopNotification;
import org.eclipse.scout.rt.client.ui.desktop.notification.IDesktopNotification;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.ExamplesBox.CloseableField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.ExamplesBox.DurationField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.ExamplesBox.MessageField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.ExamplesBox.SeverityField;
import org.eclipse.scout.widgets.shared.services.code.SeverityCodeType;

@Order(8100.0)
public class DesktopForm extends AbstractForm implements IAdvancedExampleForm {

  private IDesktopNotification m_lastNotification;

  public DesktopForm() {
    super();
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MessageField getMessageField() {
    return getFieldByClass(MessageField.class);
  }

  public SeverityField getSeverityField() {
    return getFieldByClass(SeverityField.class);
  }

  public DurationField getDurationField() {
    return getFieldByClass(DurationField.class);
  }

  public CloseableField getCloseableField() {
    return getFieldByClass(CloseableField.class);
  }

  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10)
    public class CloseButton extends AbstractCloseButton {
    }

    @Order(20)
    public class ShowNotificationButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ShowNotification");
      }

      @Override
      protected void execClickAction() {
        IStatus status = new Status(getMessageField().getValue(), getSeverityField().getValue());
        long duration = getDurationField().getValue();
        boolean closeable = getCloseableField().getValue();
        DesktopNotification notification = new DesktopNotification(status, duration, closeable);
        ClientSession.get().getDesktop().addNotification(notification);
        m_lastNotification = notification;
      }
    }

    @Order(30)
    public class RemoveNotificationButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("RemoveNotification");
      }

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("RemoveNotificationTooltip");
      }

      @Override
      protected void execClickAction() {
        ClientSession.get().getDesktop().removeNotification(m_lastNotification);
      }
    }

    @Order(40)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Order(10)
      public class MessageField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Message");
        }

        @Override
        protected void execInitField() {
          setValue("Foo Bar");
        }
      }

      @Order(50)
      public class SeverityField extends AbstractSmartField<Integer> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Severity");
        }

        @Override
        protected Class<? extends ICodeType<?, Integer>> getConfiguredCodeType() {
          return SeverityCodeType.class;
        }

        @Override
        protected void execInitField() {
          setValue(IStatus.INFO);
        }
      }

      @Order(60)
      public class DurationField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Duration");
        }

        @Override
        protected void execInitField() {
          setValue(5000);
        }
      }

      @Order(70)
      public class CloseableField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Closeable");
        }

        @Override
        protected void execInitField() {
          setValue(true);
        }
      }
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

}
