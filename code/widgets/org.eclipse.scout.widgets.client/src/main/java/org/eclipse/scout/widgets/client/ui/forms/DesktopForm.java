/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.view.IViewButton;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.notification.DesktopNotification;
import org.eclipse.scout.rt.client.ui.desktop.notification.IDesktopNotification;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
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
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.ImageLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.NativeNotificationVisibilityLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.ViewButtonLookupCall;
import org.eclipse.scout.widgets.client.ui.action.view.AbstractViewButtonPropertiesBox;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.CloseableField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.HtmlEnabledField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.LoadingField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.MessageField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.NotificationPropertiesBox.DelayField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.NotificationPropertiesBox.DurationField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.NotificationPropertiesBox.IconIdField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsLeftBox.NotificationPropertiesBox.SeverityField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsRightBox.NativeMessageField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsRightBox.NativeNotificationIconIdField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsRightBox.NativeNotificationTitleField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsRightBox.NativeNotificationVisibilityField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.NotificationsBox.NotificationsRightBox.NativeOnlyField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.OutlineButtonBox;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.OutlineButtonBox.OutlineButtonField;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.OutlineButtonBox.ViewButtonBox;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.BenchVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.HeaderVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.NavigationHandleVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.NavigationVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.DesktopForm.MainBox.StyleBox.TrackFocusField;
import org.eclipse.scout.widgets.shared.services.code.SeverityCodeType;

@Order(9000.0)
@ClassId("43e70921-af23-40c7-8b02-8e3263eb8452")
public class DesktopForm extends AbstractForm implements IAdvancedExampleForm {

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

  public OutlineButtonBox getOutlineButtonBox() {
    return getFieldByClass(OutlineButtonBox.class);
  }

  public OutlineButtonField getOutlineButtonField() {
    return getFieldByClass(OutlineButtonField.class);
  }

  public ViewButtonBox getViewButtonBox() {
    return getFieldByClass(ViewButtonBox.class);
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

  public LoadingField getLoadingField() {
    return getFieldByClass(LoadingField.class);
  }

  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  public NativeOnlyField getNativeOnlyField() {
    return getFieldByClass(NativeOnlyField.class);
  }

  public DelayField getDelayField() {
    return getFieldByClass(DelayField.class);
  }

  public HtmlEnabledField getHtmlEnabledField() {
    return getFieldByClass(HtmlEnabledField.class);
  }

  public NativeMessageField getNativeMessageField() {
    return getFieldByClass(NativeMessageField.class);
  }

  public NativeNotificationIconIdField getNativeNotificationIconIdField() {
    return getFieldByClass(NativeNotificationIconIdField.class);
  }

  public NativeNotificationTitleField getNativeNotificationTitleField() {
    return getFieldByClass(NativeNotificationTitleField.class);
  }

  public NativeNotificationVisibilityField getNativeNotificationVisibilityField() {
    return getFieldByClass(NativeNotificationVisibilityField.class);
  }

  public TrackFocusField getTrackFocusField() {
    return getFieldByClass(TrackFocusField.class);
  }

  @ClassId("756346e1-c53b-42df-b667-1aed17ee65b6")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(40)
    @ClassId("18298556-fbab-4522-9d3d-f0d6dffbdd80")
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
      @ClassId("e52b3de5-15ab-4c9b-b6a5-b48196203a15")
      public class ShowNotificationButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ShowNotification");
        }

        @Override
        protected Boolean getConfiguredDefaultButton() {
          return Boolean.TRUE;
        }

        @Override
        protected void execClickAction() {
          final IRunnable openFormRunnable = () -> {
            IStatus status = new Status(getMessageField().getValue(), getSeverityField().getValue(), getIconIdField().getValue());
            long duration = getDurationField().getValue();
            boolean closeable = getCloseableField().getValue();
            boolean loading = getLoadingField().getValue();
            boolean nativeOnly = getNativeOnlyField().getValue();
            String nativeNotificationTitle = getNativeNotificationTitleField().getValue();
            String nativeNotificationVisibility = getNativeNotificationVisibilityField().getValue();
            String nativeNotificationIconId = getNativeNotificationIconIdField().getValue();
            DesktopNotification notification = new DesktopNotification(status, duration, closeable)
                .withNativeOnly(nativeOnly)
                .withNativeNotificationTitle(nativeNotificationTitle)
                .withNativeNotificationVisibility(nativeNotificationVisibility)
                .withNativeNotificationStatus(new Status(getNativeMessageField().getValue(), getSeverityField().getValue(), 0, nativeNotificationIconId))
                .withHtmlEnabled(getHtmlEnabledField().getValue());
            notification.setLoading(loading);

            getDesktop().addNotification(notification);
          };

          int openingDelay = (getDelayField().getValue() != null ? getDelayField().getValue() : 0);
          if (openingDelay == 0) {
            ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
          }
          else {
            Jobs.schedule(() -> {
              ModelJobs.schedule(openFormRunnable, ModelJobs.newInput(ClientRunContexts.copyCurrent()));
            }, Jobs.newInput()
                .withRunContext(ClientRunContexts.copyCurrent())
                .withExecutionTrigger(Jobs.newExecutionTrigger()
                    .withStartIn(openingDelay, TimeUnit.MILLISECONDS)));
          }
        }
      }

      @Order(30)
      @ClassId("cb5ac750-283b-40d1-a0d6-5eb2fb4f5dfc")
      public class RemoveNotificationButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("RemoveNotification");
        }


        @Override
        protected void execClickAction() {
          IDesktop desktop = IDesktop.CURRENT.get();
          desktop.removeNotification(CollectionUtility.firstElement(desktop.getNotifications()));
        }
      }

      @Order(20)
      @ClassId("0281a4fd-8e22-42a0-98d4-50c7509a37f0")
      public class NotificationsLeftBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
        @ClassId("9f5314b9-6571-43ee-8049-51e694e0f81c")
        public class MessageField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Message");
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected void execInitField() {
            setValue("Foo Bar");
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected double getConfiguredGridWeightY() {
            return 0;
          }
        }

        @Order(20)
        @ClassId("b63b8e70-5780-4a2f-8550-e8c1af8a096a")
        public class NotificationPropertiesBox extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 1;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig().withSmallHGap();
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected boolean getConfiguredGridUseUiHeight() {
            return true;
          }

          @Order(50)
          @ClassId("3353d171-c2f7-4035-b1ee-8ba442b6672f")
          public class SeverityField extends AbstractSmartField<Integer> {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Severity");
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected Class<? extends ICodeType<?, Integer>> getConfiguredCodeType() {
              return SeverityCodeType.class;
            }

            @Override
            protected String getConfiguredDisplayStyle() {
              return DISPLAY_STYLE_DROPDOWN;
            }

            @Override
            protected void execInitField() {
              setValue(IStatus.INFO);
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              return ObjectUtility.nvl(rawValue, IStatus.INFO);
            }
          }

          @Order(55)
          @ClassId("f4e6a40d-3f4b-4ba9-ae14-5fc29256ad3d")
          public class IconIdField extends AbstractSmartField<String> {

            @Override
            protected String getConfiguredLabel() {
              return "IconId";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
              return IconIdLookupCall.class;
            }
          }

          @Order(60)
          @ClassId("e14318ac-05bc-436e-bce9-940a62117bb0")
          public class DurationField extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Duration");
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Duration in milliseconds while the notification is displayed.\n\nA value <= 0 indicates an infinite duration, i.e. the notification is never closed automatically.";
            }

            @Override
            protected void execInitField() {
              setValue(5000);
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              return ObjectUtility.nvl(rawValue, 0);
            }
          }

          @Order(65)
          @ClassId("30f3c198-4fc3-4b14-a43c-8bbaae50828e")
          public class DelayField extends AbstractIntegerField {

            @Override
            protected String getConfiguredLabel() {
              return "Delay";
            }

            @Override
            protected byte getConfiguredLabelPosition() {
              return LABEL_POSITION_TOP;
            }

            @Override
            protected String getConfiguredTooltipText() {
              return TEXTS.get("NativeNotificationDelayTooltip");
            }

            @Override
            protected void execInitField() {
              setValue(0);
            }

            @Override
            protected Integer execValidateValue(Integer rawValue) {
              return ObjectUtility.nvl(rawValue, 0);
            }
          }
        }

        @Order(70)
        @ClassId("51eb1a12-dba6-410d-818f-5355dd6ccd78")
        public class CloseableField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Closeable");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(true);
          }
        }

        @Order(75)
        @ClassId("f99184f9-14cd-499f-ac72-a1166eb7a9f6")
        public class LoadingField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Loading";
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
        }

        @Order(80)
        @ClassId("fac161e8-3570-46fe-8670-1b06c23f0640")
        public class HtmlEnabledField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "HTML Enabled";
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }
        }
      }

      @Order(30)
      @ClassId("7cfe5d2f-a67f-4322-81aa-fadd23454589")
      public class NotificationsRightBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Order(10)
        @ClassId("aa682456-5f7a-45f5-8c22-a1070c0c09af")
        public class NativeMessageField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Native Message";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected boolean getConfiguredMultilineText() {
            return true;
          }

          @Override
          protected int getConfiguredGridH() {
            return 2;
          }

          @Override
          protected double getConfiguredGridWeightY() {
            return 0;
          }
        }

        @Order(20)
        @ClassId("08c22faf-280b-4401-823e-0316bbbcc7a9")
        public class NativeNotificationTitleField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Native Notification Title";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected void execInitField() {
            setValue(getDesktop().getNativeNotificationDefaults().getTitle());
          }
        }

        @Order(30)
        @ClassId("58d3bcd2-95bf-4ef0-937a-cfdbfbe6137e")
        public class NativeNotificationIconIdField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Native Notification Icon Id";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return ImageLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(getDesktop().getNativeNotificationDefaults().getIconId());
          }
        }

        @Order(40)
        @ClassId("ea3451e8-c024-4bda-8033-29eaa7493aac")
        public class NativeNotificationVisibilityField extends AbstractSmartField<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Native Notification Visibility";
          }

          @Override
          protected byte getConfiguredLabelPosition() {
            return LABEL_POSITION_TOP;
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return NativeNotificationVisibilityLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(getDesktop().getNativeNotificationDefaults().getVisibility());
          }

          @Override
          protected String execValidateValue(String rawValue) {
            return ObjectUtility.nvl(rawValue, IDesktopNotification.NATIVE_NOTIFICATION_VISIBILITY_NONE);
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("NativeNotificationVisibilityTooltip");
          }

          @Override
          protected String getConfiguredDisplayStyle() {
            return DISPLAY_STYLE_DROPDOWN;
          }
        }

        @Order(50)
        @ClassId("b201fd6d-a587-4497-8977-c9c35abf02e0")
        public class NativeOnlyField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Native Only";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("NativeNotificationOnlyTooltip");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execInitField() {
            setValue(false);
          }
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

      @Override
      protected int getConfiguredGridColumnCount() {
        return 2;
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
            ModelJobs.schedule(() -> setValue(true), ModelJobs.newInput(ClientRunContexts.copyCurrent()));
          }
        }
      }

      @Order(4000)
      @ClassId("4d165ff5-68fb-4b5b-bf20-77ec3080f035")
      public class TrackFocusField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Track Focus";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execInitField() {
          setValue(getDesktop().isTrackFocus());
        }

        @Override
        protected void execChangedValue() {
          getDesktop().setTrackFocus(getValue());
        }
      }
    }

    @Order(3000)
    @ClassId("1e5b55cc-401b-43c8-b0ad-f54c37ed3300")
    public class OutlineButtonBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ViewButtons");
      }

      @Order(1000)
      @ClassId("88b121ff-e677-49d9-a990-d9dc4e7592f0")
      public class OutlineButtonField extends AbstractSmartField<IViewButton> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ViewButton");
        }

        @Override
        protected Class<? extends ILookupCall<IViewButton>> getConfiguredLookupCall() {
          return ViewButtonLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getViewButtonBox().setAction(getValue());
        }
      }

      @Order(2000)
      @ClassId("3e0346c2-ac0e-490a-ab7d-0ed2b4c1e997")
      public class ViewButtonBox extends AbstractViewButtonPropertiesBox {
      }
    }

    @Order(10000)
    @ClassId("3a968d83-719d-4b0e-b81b-7a5bfc333dd2")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
