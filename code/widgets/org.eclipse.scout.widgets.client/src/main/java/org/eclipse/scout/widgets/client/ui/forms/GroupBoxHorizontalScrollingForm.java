/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.LogicalGridLayoutConfig;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.HorizontalGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.internal.VerticalSmartGroupBoxBodyGrid;
import org.eclipse.scout.rt.client.ui.form.fields.placeholder.AbstractPlaceholderField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.notification.Notification;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox.IconIdField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox.NotificationClosableField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox.NotificationHtmlEnabled;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox.NotificationStatusField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox.NotificationTextField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ConfigurationGroupBox.ResponsiveField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example1Box;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example1Box.DefaultBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example1Box.SingleColumnBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example1Box.SingleColumnBox.CityField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example1Box.SingleColumnBox.CountryField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example2Box;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example2Box.HorizontalMonthsBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example2Box.VerticalMonthsBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box.OuterScrollableBox.ScrollableBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box.SectionBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box.SectionBox.CommentField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box.SectionBox.CompanyField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box.SectionBox.FirstNameField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.ExamplesBox.Example3Box.SectionBox.LastNameField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.VisibilityBox;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.VisibilityBox.LabelVisibleField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.VisibilityBox.Placeholder2Field;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.VisibilityBox.VisibleCompanyField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.VisibilityBox.VisibleFirstNameField;
import org.eclipse.scout.widgets.client.ui.forms.GroupBoxHorizontalScrollingForm.MainBox.VisibilityBox.VisibleLastNameField;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractMonthsBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.StatusSeverityLookupCall;

@ClassId("92627520-ba32-4784-82f5-576a0432f949")
public class GroupBoxHorizontalScrollingForm extends AbstractForm implements IPageForm {

  protected boolean m_disableHorizontalScrolling = false;

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("GroupBoxHorizontalScrolling");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  protected void execInitForm() {
    if (m_disableHorizontalScrolling) {
      for (IFormField field : getAllFields()) {
        if (field instanceof IGroupBox) {
          IGroupBox box = ((IGroupBox) field);
          box.setBodyLayoutConfig(box.getBodyLayoutConfig().copy().withMinWidth(0));
        }
      }
    }
  }

  public CityField getCityField() {
    return getFieldByClass(CityField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public CommentField getCommentField() {
    return getFieldByClass(CommentField.class);
  }

  public CompanyField getCompanyField() {
    return getFieldByClass(CompanyField.class);
  }

  public CountryField getCountryField() {
    return getFieldByClass(CountryField.class);
  }

  public DefaultBox getDefaultBox() {
    return getFieldByClass(DefaultBox.class);
  }

  public Example1Box getExample1Box() {
    return getFieldByClass(Example1Box.class);
  }

  public Example2Box getExample2Box() {
    return getFieldByClass(Example2Box.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public FirstNameField getFirstNameField() {
    return getFieldByClass(FirstNameField.class);
  }

  public HorizontalMonthsBox getHorizontalMonthsBox() {
    return getFieldByClass(HorizontalMonthsBox.class);
  }

  public LastNameField getLastNameField() {
    return getFieldByClass(LastNameField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public Example3Box getExample3Box() {
    return getFieldByClass(Example3Box.class);
  }

  public LabelVisibleField getLabelVisibleField() {
    return getFieldByClass(LabelVisibleField.class);
  }

  public Placeholder2Field getPlaceholder2Field() {
    return getFieldByClass(Placeholder2Field.class);
  }

  public ScrollableBox getScrollableBox() {
    return getFieldByClass(ScrollableBox.class);
  }

  public SectionBox getSectionBox() {
    return getFieldByClass(SectionBox.class);
  }

  public SingleColumnBox getSingleColumnBox() {
    return getFieldByClass(SingleColumnBox.class);
  }

  public VerticalMonthsBox getVerticalMonthsBox() {
    return getFieldByClass(VerticalMonthsBox.class);
  }

  public VisibilityBox getVisibilityBox() {
    return getFieldByClass(VisibilityBox.class);
  }

  public VisibleCompanyField getVisibleCompanyField() {
    return getFieldByClass(VisibleCompanyField.class);
  }

  public VisibleFirstNameField getVisibleFirstNameField() {
    return getFieldByClass(VisibleFirstNameField.class);
  }

  public VisibleLastNameField getVisibleLastNameField() {
    return getFieldByClass(VisibleLastNameField.class);
  }

  public ConfigurationGroupBox getConfigurationGroupBox() {
    return getFieldByClass(ConfigurationGroupBox.class);
  }

  public IconIdField getIconIdField() {
    return getFieldByClass(IconIdField.class);
  }

  public ResponsiveField getResponsiveField() {
    return getFieldByClass(ResponsiveField.class);
  }

  public NotificationStatusField getNotificationStatusField() {
    return getFieldByClass(NotificationStatusField.class);
  }

  public NotificationTextField getNotificationTextField() {
    return getFieldByClass(NotificationTextField.class);
  }

  public NotificationClosableField getNotificationClosableField() {
    return getFieldByClass(NotificationClosableField.class);
  }

  public NotificationHtmlEnabled getNotificationHtmlEnabledField() {
    return getFieldByClass(NotificationHtmlEnabled.class);
  }

  protected void handleNotificationChanged() {
    Integer severity = getNotificationStatusField().getValue();
    if (severity != null) {
      String message = getNotificationTextField().getValue() != null ? getNotificationTextField().getValue() : "";
      Notification notification = new Notification(new Status(message, severity),
          getNotificationClosableField().getValue(),
          getNotificationHtmlEnabledField().getValue(),
          getIconIdField().getValue());
      getVerticalMonthsBox().setNotification(notification);
    }
    else {
      getVerticalMonthsBox().removeNotification();
    }
  }

  @Order(10)
  @ClassId("1d871d96-413c-404b-929b-1c8b77bff418")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
      return super.getConfiguredBodyLayoutConfig()
          .withMinWidth(400);
    }

    @Override
    protected boolean getConfiguredBorderVisible() {
      return false;
    }

    @Override
    protected String getConfiguredBorderDecoration() {
      return BORDER_DECORATION_LINE;
    }

    @Override
    protected void execInitField() {
      /*
       * The MainBox as well as the last content groupbox are horizontal scrollable. Because the MainBox is the root-groupbox
       * there would be no padding around the content. In this particular case this is only partly what we want: While we are happy without
       * the top-padding, we need to add a bottom-padding to prevent the scrollbars from being rendered above each other.
       * --> This is achieved with this custom css-class. (see GroupBoxForm.less)
       */
      setCssClass("add-space-after-content");
    }

    @Order(10)
    @ClassId("7b072f9d-2c2b-4106-aaab-e2ca7880fa75")
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected int getConfiguredGridColumnCount() {
        return 5;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      @ClassId("a51c3473-864a-4f7c-81af-0e02889daf6e")
      public class Example1Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 5;
        }

        @Override
        protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
          return super.getConfiguredBodyLayoutConfig()
              .withMinWidth(500);
        }

        @Override
        protected void execInitField() {
          /*
           * This groupbox as well as the content groupboxes are horizontal scrollable. As a result of the borderVisible-property being set to false
           * there would be no padding around the content. In this particular case this is only partly what we want: While we are happy without
           * the top-padding, we need to add a bottom-padding to prevent the scrollbars from being rendered above each other.
           * --> This is achieved with this custom css-class. (see GroupBoxForm.less)
           */
          setCssClass("add-space-after-content");
        }

        @Order(10)
        @ClassId("99bf4c73-a7f5-482b-ae4c-1346ac23df60")
        public class DefaultBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected int getConfiguredGridW() {
            return 3;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withMinWidth(500);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Default");
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("DefaultBoxTooltip");
          }

          @Order(10)
          @ClassId("ec14070e-3256-4cf3-b50d-75cf6dc14d1b")
          public class FirstNameField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("FirstName");
            }
          }

          @Order(20)
          @ClassId("d4fe9803-c151-4c36-858d-c45f05d4331b")
          public class LastNameField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("LastName");
            }
          }

          @Order(30)
          @ClassId("0ff86384-6972-4401-8183-b40df562878f")
          public class CompanyField extends AbstractStringField {

            @Override
            protected int getConfiguredGridH() {
              return 2;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Company");
            }

            @Override
            protected boolean getConfiguredMultilineText() {
              return true;
            }
          }

          @Order(50)
          @ClassId("95d1ea5c-eea2-4abf-9a6d-35864319d4e5")
          public class CommentField extends AbstractStringField {

            @Override
            protected int getConfiguredGridW() {
              return 2;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Comments");
            }
          }
        }

        @Order(20)
        @ClassId("9b878df0-2c85-48d9-a04c-0002dae37cdf")
        public class SingleColumnBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridColumnCount() {
            return 1;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withMinWidth(250);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("SingleColumnBox");
          }

          @Order(10)
          @ClassId("6c1999cc-6c13-4f1b-a96c-8a7ba1c7bf41")
          public class CityField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("City");
            }
          }

          @Order(20)
          @ClassId("d4146d5b-fb3b-400a-bf76-a5fe493de146")
          public class CountryField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Country");
            }
          }
        }
      }

      @Order(20)
      @ClassId("91804532-24df-418f-8771-3c996261d6a7")
      public class Example2Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 5;
        }

        @Order(10)
        @ClassId("eb47c36c-8cb0-4472-8fe9-54d80041fb42")
        public class VerticalMonthsBox extends AbstractMonthsBox {
          @Override
          protected Class<? extends IGroupBoxBodyGrid> getConfiguredBodyGrid() {
            return VerticalSmartGroupBoxBodyGrid.class;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withMinWidth(320);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("VerticalLayout");
          }
        }

        @Order(20)
        @ClassId("42d77b41-fdb2-4ae3-a1b5-335adbd18e3a")
        public class HorizontalMonthsBox extends AbstractMonthsBox {
          @Override
          protected Class<? extends IGroupBoxBodyGrid> getConfiguredBodyGrid() {
            return HorizontalGroupBoxBodyGrid.class;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 3;
          }

          @Override
          protected int getConfiguredGridW() {
            return 3;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withMinWidth(500);
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("HorizontalLayout");
          }
        }

      }

      @Order(30)
      @ClassId("df23c6d6-f8e8-492c-aaad-8a8d8f2601df")
      public class Example3Box extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 5;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        public CompanyField getCompanyField() {
          return getFieldByClass(CompanyField.class);
        }

        @Order(10)
        @ClassId("f2d8828d-6556-46ef-ac95-0c8cf1e956fc")
        public class OuterScrollableBox extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withMinWidth(250);
          }

          @Override
          protected boolean getConfiguredFillVertical() {
            // prevent growing bigger than configured height in pixel
            return false;
          }

          @Override
          protected int getConfiguredHeightInPixel() {
            return 184;
          }

          @Order(10)
          @ClassId("dce746eb-63c8-4ca3-9e84-fc24e50dec35")
          public class ScrollableBox extends AbstractMonthsBox {

            @Override
            protected int getConfiguredGridColumnCount() {
              return 1;
            }

            @Override
            protected int getConfiguredGridW() {
              return 2;
            }

            @Override
            protected boolean getConfiguredFillVertical() {
              // prevent growing bigger than configured height in pixel
              return false;
            }

            @Override
            protected int getConfiguredHeightInPixel() {
              /* We want 3 rows which - when scrolled to the top - are neatly aligned with the 3 rows of the neighboring groupbox.
                 1. In the browser we "measured" the resulting height of the neighboring groupbox with 3 rows: 184px.
                 2. We subtract the 16px padding-bottom size of the group-box-body. Because the padding does not hide or clip the
                    content and we don't want to see half of the 4th row through the padding.
                 3. For the horizontal scrolling we wrap this groubox with an outer groupbox with 184px height. Because otherwise
                    the horizontal scrollbar would overlap the formfield in the 3rd row and would not be aligned with the horizontal
                    scrollbar of the neighboring groupbox.
              */
              return 184 - 16;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Scrollable");
            }

            @Override
            protected TriState getConfiguredScrollable() {
              return TriState.TRUE;
            }
          }
        }

        @Order(20)
        @ClassId("7321616d-ae95-4d75-98cb-3c8610569a44")
        public class SectionBox extends AbstractGroupBox {

          @Override
          protected boolean getConfiguredExpandable() {
            return true;
          }

          @Override
          protected int getConfiguredGridH() {
            return 3;
          }

          @Override
          protected int getConfiguredGridW() {
            return 3;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Section");
          }

          @Override
          protected boolean getConfiguredFillVertical() {
            return false;
          }

          @Override
          protected int getConfiguredGridColumnCount() {
            return 2;
          }

          @Override
          protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
            return super.getConfiguredBodyLayoutConfig()
                .withMinWidth(500);
          }

          @Order(10)
          @ClassId("5c15bd53-498f-4720-ad3f-687f124d12f7")
          public class FirstNameField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("FirstName");
            }
          }

          @Order(20)
          @ClassId("3b4faf1d-e348-4623-97bf-15695db7e7ba")
          public class LastNameField extends AbstractStringField {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("LastName");
            }
          }

          @Order(30)
          @ClassId("eb17456d-d1fd-468c-a38e-7f7ec02db178")
          public class CompanyField extends AbstractStringField {

            @Override
            protected int getConfiguredGridH() {
              return 2;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Company");
            }

            @Override
            protected boolean getConfiguredMultilineText() {
              return true;
            }
          }

          @Order(50)
          @ClassId("8a44d43c-f4c3-4163-8b76-e6799dd2fadc")
          public class CommentField extends AbstractStringField {

            @Override
            protected int getConfiguredGridW() {
              return 2;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Comments");
            }
          }
        }
      }
    }

    @Order(20)
    @ClassId("ee0edd4c-e97e-4ffc-8ed5-2db26fa7da7e")
    public class ConfigurationGroupBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Configuration";
      }

      @Order(10)
      @ClassId("6f4f5ff9-da83-4b16-b448-6562e429f67c")
      public class NotificationStatusField extends AbstractSmartField<Integer> {

        @Override
        protected String getConfiguredLabel() {
          return "Notification Status";
        }

        @Override
        protected Class<? extends ILookupCall<Integer>> getConfiguredLookupCall() {
          return StatusSeverityLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          handleNotificationChanged();
        }
      }

      @Order(20)
      @ClassId("4b5055c0-0b1f-4480-b284-fa1fbf585e42")
      public class NotificationTextField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "Notification Text";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 500;
        }

        @Override
        protected void execInitField() {
          setValue("I'm a notification.");
        }

        @Override
        protected Class<? extends IValueField> getConfiguredMasterField() {
          return NotificationStatusField.class;
        }

        @Override
        protected boolean getConfiguredMasterRequired() {
          return true;
        }

        @Override
        protected void execChangedMasterValue(Object newMasterValue) {
          execChangedValue();
        }

        @Override
        protected void execChangedValue() {
          handleNotificationChanged();
        }
      }

      @Order(25)
      @ClassId("4cb0be95-019c-437b-a803-4c5a9df4a39e")
      public class IconIdField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return "IconId";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return IconIdLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          handleNotificationChanged();
        }
      }

      @Order(30)
      @ClassId("a260af28-8947-48ab-a39f-f85d3371c19a")
      public class NotificationClosableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Notification Closable";
        }

        @Override
        protected void execInitField() {
          setValue(false);
        }

        @Override
        protected void execChangedValue() {
          handleNotificationChanged();
        }
      }

      @Order(40)
      @ClassId("7d1650f3-d225-4851-b35c-37652da3e4ef")
      public class NotificationHtmlEnabled extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Notification HTML Enabled";
        }

        @Override
        protected void execInitField() {
          setValue(false);
        }

        @Override
        protected void execChangedValue() {
          handleNotificationChanged();
        }
      }

      @Order(2000)
      @ClassId("cd6d2e17-36e0-47d8-9a2c-e382c730206a")
      public class ResponsiveField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VerticalLayout") + " Responsive";
        }

        @Override
        protected boolean getConfiguredTriStateEnabled() {
          return true;
        }

        @Override
        protected void execInitField() {
          setValue(null);
        }

        @Override
        protected void execChangedValue() {
          if (!isLoading()) {
            getVerticalMonthsBox().setResponsive(TriState.parse(getValue()));
          }
        }
      }
    }

    @Order(30)
    @ClassId("1f9d1423-4e76-475f-9c52-ff1cf187ff68")
    public class VisibilityBox extends AbstractGroupBox {

      @Override
      protected double getConfiguredGridWeightY() {
        return 0.0;
      }

      @Override
      protected LogicalGridLayoutConfig getConfiguredBodyLayoutConfig() {
        return super.getConfiguredBodyLayoutConfig()
            .withMinWidth(620);
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("FieldVisibility");
      }

      @Order(10)
      @ClassId("b140a159-b9bf-4f56-bdfd-0e393ec20ff8")
      public class VisibleFirstNameField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleFirstName");
        }

        @Override
        protected void execChangedValue() {
          getFirstNameField().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getFirstNameField().isVisible());
        }
      }

      @Order(20)
      @ClassId("b0d00aa2-48c9-4447-a0c5-1a5d0ffa9b8d")
      public class VisibleLastNameField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleLastName");
        }

        @Override
        protected void execChangedValue() {
          getLastNameField().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getLastNameField().isVisible());
        }
      }

      @Order(30)
      @ClassId("df52691d-5e77-46e3-8540-d86eb837badf")
      public class VisibleCompanyField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleCompany");
        }

        @Override
        protected void execChangedValue() {
          getCompanyField().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getCompanyField().isVisible());
        }
      }

      @Order(40)
      @ClassId("59e200d9-ed9e-421b-90ad-e41b27ae808e")
      public class Placeholder1Field extends AbstractPlaceholderField {
      }

      @Order(60)
      @ClassId("382726bd-ba2c-4d77-9c4f-6af526b8feb8")
      public class VisibleField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleScrollable");
        }

        @Override
        protected void execChangedValue() {
          getScrollableBox().setVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getScrollableBox().isVisible());
        }
      }

      @Order(70)
      @ClassId("f1f338d4-3fec-4c44-9ba7-4c352e31bd57")
      public class LabelVisibleField extends AbstractBooleanField {

        @Override
        protected String getConfiguredFont() {
          return "ITALIC";
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VisibleDefaultGroupBoxLabel");
        }

        @Override
        protected void execChangedValue() {
          getDefaultBox().setLabelVisible(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getDefaultBox().isLabelVisible());
        }
      }

      @Order(80)
      @ClassId("161201e3-935c-49b7-b25a-362d14e79f3d")
      public class Placeholder2Field extends AbstractPlaceholderField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }
      }
    }

    @Order(30)
    @ClassId("b96c57a5-7777-4590-b074-ecb3f67d98ac")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
