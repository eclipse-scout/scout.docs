/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.scout.rt.client.ui.IWidget;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.label.AbstractLabel;
import org.eclipse.scout.rt.client.ui.popup.AbstractFormPopup;
import org.eclipse.scout.rt.client.ui.popup.AbstractWidgetPopup;
import org.eclipse.scout.rt.client.ui.popup.IPopup;
import org.eclipse.scout.rt.client.ui.popup.IWidgetPopup;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.BeanUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.FormPopup.ContentForm;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.FormPopup.ContentForm.MainBox.FieldsBox;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.GroupBoxWidgetPopup.ContentGroupBox;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.LabelWidgetPopup.Label;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.ExampleBox.OpenPopupButton;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.CloseOnAnchorMouseDownField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.CloseOnMouseDownOutsideField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.CloseOnOtherPopupOpenField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.HorizontalAlignmentField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.HorizontalSwitchField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.PopupTypeField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.ScrollTypeField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.TrimHeightField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.TrimWidthField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.UseButtonAsAnchorField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.VerticalAlignmentField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.VerticalSwitchField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.PropertiesBox.WithArrowField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.WidgetPopupPropertiesBox.ClosableField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.WidgetPopupPropertiesBox.MovableField;
import org.eclipse.scout.widgets.client.ui.forms.PopupForm.MainBox.WidgetPopupPropertiesBox.ResizableField;
import org.eclipse.scout.widgets.shared.Icons;

/**
 * @since 9.0
 */
@ClassId("3382f44d-8e6c-48f8-9aee-69c45529d431")
public class PopupForm extends AbstractForm implements IAdvancedExampleForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("PopupField");
  }

  @Override
  protected void initConfig() {
    super.initConfig();
    // init default values
    getPopupTypeField().setValue(PopupType.GROUP_BOX_WIDGET_POPUP_TYPE);
    getCloseOnAnchorMouseDownField().setValue(true);
    getCloseOnMouseDownOutsideField().setValue(true);
    getCloseOnOtherPopupOpenField().setValue(true);
    getVerticalSwitchField().setValue(true);
    getTrimHeightField().setValue(true);
    getHorizontalAlignmentField().setValue(IPopup.POPUP_ALIGNMENT_LEFTEDGE);
    getVerticalAlignmentField().setValue(IPopup.POPUP_ALIGNMENT_BOTTOM);
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public FieldsBox getFieldsBox() {
    return getFieldByClass(FieldsBox.class);
  }

  public PopupTypeField getPopupTypeField() {
    return getFieldByClass(PopupTypeField.class);
  }

  public CloseOnAnchorMouseDownField getCloseOnAnchorMouseDownField() {
    return getFieldByClass(CloseOnAnchorMouseDownField.class);
  }

  public CloseOnMouseDownOutsideField getCloseOnMouseDownOutsideField() {
    return getFieldByClass(CloseOnMouseDownOutsideField.class);
  }

  public CloseOnOtherPopupOpenField getCloseOnOtherPopupOpenField() {
    return getFieldByClass(CloseOnOtherPopupOpenField.class);
  }

  public HorizontalSwitchField getHorizontalSwitchField() {
    return getFieldByClass(HorizontalSwitchField.class);
  }

  public VerticalSwitchField getVerticalSwitchField() {
    return getFieldByClass(VerticalSwitchField.class);
  }

  public TrimWidthField getTrimWidthField() {
    return getFieldByClass(TrimWidthField.class);
  }

  public TrimHeightField getTrimHeightField() {
    return getFieldByClass(TrimHeightField.class);
  }

  public WithArrowField getWithArrowField() {
    return getFieldByClass(WithArrowField.class);
  }

  public HorizontalAlignmentField getHorizontalAlignmentField() {
    return getFieldByClass(HorizontalAlignmentField.class);
  }

  public VerticalAlignmentField getVerticalAlignmentField() {
    return getFieldByClass(VerticalAlignmentField.class);
  }

  public ScrollTypeField getScrollTypeField() {
    return getFieldByClass(ScrollTypeField.class);
  }

  public ClosableField getClosableField() {
    return getFieldByClass(ClosableField.class);
  }

  public ResizableField getResizableField() {
    return getFieldByClass(ResizableField.class);
  }

  public MovableField getMovableField() {
    return getFieldByClass(MovableField.class);
  }

  public OpenPopupButton getOpenPopupButton() {
    return getFieldByClass(OpenPopupButton.class);
  }

  public UseButtonAsAnchorField getUseButtonAsAnchorField() {
    return getFieldByClass(UseButtonAsAnchorField.class);
  }

  public PropertiesBox getPropertiesBox() {
    return getFieldByClass(PropertiesBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("ea905405-8ba3-4518-9b5f-5a61ff66fd8e")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("98940da7-8d50-4b82-b643-1b4ea8a7af9b")
    public class ExampleBox extends AbstractGroupBox {

      @Order(10)
      @ClassId("6cbaa82b-3c2e-472e-8bd5-8e87d0077b1f")
      public class OpenPopupButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("OpenPopup");
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() {
          openPopup();
        }

        @Override
        protected Boolean getConfiguredDefaultButton() {
          return true;
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.ENTER;
        }
      }
    }

    @Order(10)
    @ClassId("9f73484b-e3fb-499a-a8fd-42cd64181579")
    public class PropertiesBox extends AbstractGroupBox {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Properties");
      }

      @Order(10)
      @ClassId("5875195c-b747-494d-ad4e-ca6d2866e00a")
      public class PopupTypeField extends AbstractSmartField<PopupType> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("PopupType");
        }

        @Override
        protected Class<? extends ILookupCall<PopupType>> getConfiguredLookupCall() {
          return PopupTypeLookupCall.class;
        }

        @Override
        protected void initFieldInternal() {
          setValue(PopupType.LABEL_WIDGET_POPUP_TYPE);
        }
      }

      @Order(20)
      @ClassId("72660a90-f46d-4a8c-9660-c9b79fc26cd8")
      public class UseButtonAsAnchorField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UseButtonAsAnchor");
        }
      }

      @Order(2000)
      @ClassId("20c07f16-992a-46f3-9b2b-ad5fb919bc62")
      public class CloseOnAnchorMouseDownField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CloseOnAnchorMouseDown");
        }
      }

      @Order(3000)
      @ClassId("0830f16e-da89-4739-8206-e16f5455afdc")
      public class CloseOnMouseDownOutsideField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CloseOnMouseDownOutside");
        }
      }

      @Order(4000)
      @ClassId("22cc6889-893b-410b-b9b2-e03886ebe494")
      public class CloseOnOtherPopupOpenField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CloseOnOtherPopupOpen");
        }
      }

      @Order(5000)
      @ClassId("98a209ef-6933-48dd-9f28-d11a532544a1")
      public class HorizontalSwitchField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HorizontalSwitch");
        }
      }

      @Order(6000)
      @ClassId("64336e28-5f55-4d36-b105-ba242c7b6ba3")
      public class VerticalSwitchField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VerticalSwitch");
        }
      }

      @Order(7000)
      @ClassId("8966479c-9d3b-4d55-a44e-1995787a7694")
      public class TrimWidthField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TrimWidth");
        }
      }

      @Order(8000)
      @ClassId("7215be27-1efe-485b-8af6-9d963fcc5576")
      public class TrimHeightField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TrimHeight");
        }
      }

      @Order(9000)
      @ClassId("28859d88-3ec8-427c-b7cc-f0641de30a13")
      public class WithArrowField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("WithArrow");
        }
      }

      @Order(9500)
      @ClassId("95b5cbfc-d7ec-4940-9065-77976d14b597")
      public class ScrollTypeField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ScrollType");
        }

        @Override
        protected void execInitField() {
          setValue(IPopup.SCROLL_TYPE_REMOVE);
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return ScrollTypeLookupCall.class;
        }
      }

      @Order(10000)
      @ClassId("bb3abede-ebd4-4d1d-923a-eb45ee346fd9")
      public class HorizontalAlignmentField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HorizontalAlignment");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return HorizontalAlignmentLookupCall.class;
        }
      }

      @Order(11000)
      @ClassId("70986d52-7795-4f9f-a352-ed853cae19bf")
      public class VerticalAlignmentField extends AbstractSmartField<String> {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("VerticalAlignment");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return VerticalAlignmentLookupCall.class;
        }
      }
    }

    @Order(30)
    @ClassId("d597aa61-fa64-4107-842a-71c179cd0791")
    public class WidgetPopupPropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Widget Popup Properties";
      }

      @Order(10)
      @ClassId("cb68a9be-7356-4582-87cb-137122507b61")
      public class ClosableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Closable";
        }
      }

      @Order(20)
      @ClassId("4c5f42fa-16fa-4c30-a4f9-0301ff729530")
      public class MovableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Movable";
        }
      }

      @Order(30)
      @ClassId("3df5d387-55b1-4729-82d9-5502f167aa1c")
      public class ResizableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Resizable";
        }
      }
    }

    @Order(40)
    @ClassId("1ab9e9f5-b5f8-481c-91d2-c3814f1edd22")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  protected void openPopup() {
    IWidgetPopup<?> popup = getPopupTypeField().getValue().newInstance();
    popup.setAnchor(getUseButtonAsAnchorField().isChecked() ? getOpenPopupButton() : null);
    popup.setCloseOnAnchorMouseDown(getCloseOnAnchorMouseDownField().isChecked());
    popup.setCloseOnMouseDownOutside(getCloseOnMouseDownOutsideField().isChecked());
    popup.setCloseOnOtherPopupOpen(getCloseOnOtherPopupOpenField().isChecked());
    popup.setHorizontalSwitch(getHorizontalSwitchField().isChecked());
    popup.setVerticalSwitch(getVerticalSwitchField().isChecked());
    popup.setTrimWidth(getTrimWidthField().isChecked());
    popup.setTrimHeight(getTrimHeightField().isChecked());
    popup.setWithArrow(getWithArrowField().isChecked());
    popup.setHorizontalAlignment(getHorizontalAlignmentField().getValue());
    popup.setVerticalAlignment(getVerticalAlignmentField().getValue());
    popup.setScrollType(getScrollTypeField().getValue());
    popup.setClosable(getClosableField().getValue());
    popup.setMovable(getMovableField().getValue());
    popup.setResizable(getResizableField().getValue());
    popup.open();
  }

  public enum PopupType {

    LABEL_WIDGET_POPUP_TYPE(LabelWidgetPopup.class),
    GROUP_BOX_WIDGET_POPUP_TYPE(GroupBoxWidgetPopup.class),
    FORM_POPUP_TYPE(FormPopup.class);

    private final Class<? extends IWidgetPopup<? extends IWidget>> m_popupClass;

    PopupType(Class<? extends IWidgetPopup<? extends IWidget>> popupClass) {
      m_popupClass = popupClass;
    }

    public IWidgetPopup<? extends IWidget> newInstance() {
      return BeanUtility.createInstance(m_popupClass);
    }

    public String getTitle() {
      return m_popupClass.getSimpleName();
    }
  }

  @ClassId("60fb05cc-ecb7-4618-ada6-47f51793fc8c")
  public static class LabelWidgetPopup extends AbstractWidgetPopup<Label> {

    @Override
    protected String getConfiguredCssClass() {
      return "label-widget-popup";
    }

    @Override
    protected Class<Label> getConfiguredContent() {
      return Label.class;
    }

    @ClassId("1ae92e7a-798d-4d46-af56-d65a1b5cfdf2")
    public class Label extends AbstractLabel {
      @Override
      protected String getConfiguredValue() {
        return "<h2>Hi, I'm a popup!</h2>" +
            "<p>This widget popup contains a label to display some text.</p>";
      }

      @Override
      protected boolean getConfiguredHtmlEnabled() {
        return true;
      }
    }
  }

  @ClassId("cbc18e7b-8d06-4aed-bc9e-d2601b7ec020")
  public static class GroupBoxWidgetPopup extends AbstractWidgetPopup<ContentGroupBox> {

    @Override
    protected Class<ContentGroupBox> getConfiguredContent() {
      return ContentGroupBox.class;
    }

    @ClassId("87826120-91f3-482b-8c20-6b5fca02b17a")
    public class ContentGroupBox extends AbstractGroupBox {

      @Order(100)
      @ClassId("0edad265-fc89-4aa9-9de3-ff628c29973f")
      public class TitleField extends AbstractLabelField {

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected boolean getConfiguredHtmlEnabled() {
          return true;
        }

        @Override
        protected void execInitField() {
          setValue("<b>Hi, I am WidgetPopup containing a group box!</b>");
        }
      }

      @Order(200)
      @ClassId("30a073a2-fe7f-4205-8491-98fd519252f8")
      public class FirstStringField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "First field";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(210)
      @ClassId("cc161625-d6e1-4edc-ad69-ac48043f1ec2")
      public class SecondStringField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return "Second field";
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(220)
      @ClassId("c6afd29f-e5d0-4f1f-98b5-4234c3d60b99")
      public class ImageField extends AbstractImageField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected String getConfiguredImageId() {
          return Icons.World;
        }
      }
    }
  }

  @ClassId("b83a9d3a-61f0-422f-9004-09e2d010935a")
  public static class FormPopup extends AbstractFormPopup<ContentForm> {

    @Override
    protected ContentForm createForm() {
      return new ContentForm();
    }

    @ClassId("58ced198-1d92-4415-93dd-520964deb3a1")
    public class ContentForm extends AbstractForm {

      @Order(10)
      @ClassId("27ef89a8-dda9-4509-8704-318501260fa5")
      public class MainBox extends AbstractGroupBox {

        @Override
        protected String getConfiguredMenuBarPosition() {
          return MENU_BAR_POSITION_BOTTOM;
        }

        @Order(10)
        @ClassId("caa96e55-deba-46c9-8f7c-3a328596a6bd")
        public class FieldsBox extends AbstractGroupBox {

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Order(10)
          @ClassId("8785a90f-ea9c-451c-b629-70b00024bfa5")
          public class FirstStringField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() {
              return "First field";
            }

            @Override
            protected int getConfiguredMaxLength() {
              return 128;
            }
          }

          @Order(20)
          @ClassId("e8ebcbc8-60bf-46a1-b4ea-8c452267ba24")
          public class SecondStringField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() {
              return "Second field";
            }

            @Override
            protected int getConfiguredMaxLength() {
              return 128;
            }
          }

          @Order(30)
          @ClassId("993bd266-27e4-4618-9998-0190bfd64cc0")
          public class ThirdStringField extends AbstractStringField {
            @Override
            protected String getConfiguredLabel() {
              return "Third field";
            }

            @Override
            protected int getConfiguredMaxLength() {
              return 128;
            }
          }

          @Order(40)
          @ClassId("da7f1601-086b-4d87-bd23-451652b4e4dd")
          public class ImageField extends AbstractImageField {

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            public String getImageId() {
              return Icons.Star;
            }
          }
        }

        @Order(20)
        @ClassId("433b2b32-2e11-4a45-b3a1-76abe87f9434")
        public class CloseButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("CloseButton");
          }

          @Override
          protected void execClickAction() {
            ContentForm.this.doClose();
          }
        }
      }
    }
  }

  @ClassId("1c5e073f-d5a6-489e-b6da-bbc6d7533925")
  public static class PopupTypeLookupCall extends LocalLookupCall<PopupType> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<LookupRow<PopupType>> execCreateLookupRows() {
      return Stream
          .of(PopupType.values())
          .map(e -> new LookupRow<>(e, e.getTitle()))
          .collect(Collectors.toList());
    }
  }

  @ClassId("0a916a2e-b37c-428f-b570-66b1c417d306")
  public static class HorizontalAlignmentLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      return Stream
          .of(IPopup.POPUP_ALIGNMENT_LEFTEDGE, IPopup.POPUP_ALIGNMENT_LEFT, IPopup.POPUP_ALIGNMENT_CENTER, IPopup.POPUP_ALIGNMENT_RIGHT, IPopup.POPUP_ALIGNMENT_RIGHTEDGE)
          .map(s -> new LookupRow<>(s, s))
          .collect(Collectors.toList());
    }
  }

  @ClassId("4d6bad4e-2ab6-4747-be6a-ced03317ad90")
  public static class VerticalAlignmentLookupCall extends LocalLookupCall<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      return Stream
          .of(IPopup.POPUP_ALIGNMENT_TOPEDGE, IPopup.POPUP_ALIGNMENT_TOP, IPopup.POPUP_ALIGNMENT_CENTER, IPopup.POPUP_ALIGNMENT_BOTTOM, IPopup.POPUP_ALIGNMENT_BOTTOMEDGE)
          .map(s -> new LookupRow<>(s, s))
          .collect(Collectors.toList());
    }
  }

  @ClassId("724db415-ce21-4ee3-aedd-27c679f9b561")
  public static class ScrollTypeLookupCall extends LocalLookupCall<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      return Stream
          .of(IPopup.SCROLL_TYPE_REMOVE, IPopup.SCROLL_TYPE_POSITION, IPopup.SCROLL_TYPE_LAYOUT_AND_POSITION)
          .map(s -> new LookupRow<>(s, s))
          .collect(Collectors.toList());
    }
  }

}
