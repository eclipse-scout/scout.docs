package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
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
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.widgets.client.ClientSession;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.CloseableField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.DurationField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.MessageField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.SeverityField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.BenchVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.HeaderVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.NavigationHandleVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.NavigationVisibleButton;
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

  public StyleBox getStyleBox() {
    return getFieldByClass(StyleBox.class);
  }

  public HeaderVisibleButton getHeaderVisibleButton() {
    return getFieldByClass(HeaderVisibleButton.class);
  }

  public BenchVisibleButton getBenchVisibleButton() {
    return getFieldByClass(BenchVisibleButton.class);
  }

  public NavigationHandleVisibleButton getNavigationHandleVisibleButton() {
    return getFieldByClass(NavigationHandleVisibleButton.class);
  }

  public NavigationVisibleButton getNavigationVisibleButton() {
    return getFieldByClass(NavigationVisibleButton.class);
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

    @Order(40)
    public class NotificationsBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Notifications");
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

    @Order(2000)
    @ClassId("753dc7e4-e1b9-4016-a5b5-0ce1b682b6e6")
    public class StyleBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("DisplayStyle");
      }

      @Order(1000)
      @ClassId("3db84b66-1c8f-478a-910d-27922fac5bad")
      public class NavigationVisibleButton extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("NavigationVisible");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getDesktop().isNavigationVisible());
        }

        @Override
        protected void execChangedValue() {
          getDesktop().setNavigationVisible(getValue());
        }

      }

      @Order(1500)
      @ClassId("0f1681fe-5e1b-45a0-8007-a69c13df4a3a")
      public class NavigationHandleVisibleButton extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("NavigationHandleVisible");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getDesktop().isNavigationHandleVisible());
        }

        @Override
        protected void execChangedValue() {
          getDesktop().setNavigationHandleVisible(getValue());
        }
      }

      @Order(2000)
      @ClassId("6c2bc3b0-ebb5-4c6f-8227-4002d81a355c")
      public class HeaderVisibleButton extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HeaderVisible");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getDesktop().isHeaderVisible());
        }

        @Override
        protected void execChangedValue() {
          getDesktop().setHeaderVisible(getValue());
        }
      }

      @Order(3000)
      @ClassId("6fedb013-f83c-4d07-9303-a48f0e456f60")
      public class BenchVisibleButton extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("BenchVisible");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getDesktop().isBenchVisible());
        }

        @Override
        protected void execChangedValue() {
          getDesktop().setBenchVisible(getValue());
          if (!getValue()) {
            MessageBoxes.createOk().withHeader(TEXTS.get("Help0")).withYesButtonText(TEXTS.get("GetBenchBack")).withDisplayParent(getDesktop()).show();

            // Need to schedule a model job otherwise it would fail due to the loop detection
            ModelJobs.schedule(new IRunnable() {

              @Override
              public void run() throws Exception {
                setValue(true);
              }

            }, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
          }
        }
      }

    }

    @Order(10000)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

}
