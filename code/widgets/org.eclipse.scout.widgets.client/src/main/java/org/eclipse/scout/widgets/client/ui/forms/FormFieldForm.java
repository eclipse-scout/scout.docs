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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.ValueFieldMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IStatusMenuMapping;
import org.eclipse.scout.rt.client.ui.form.fields.StatusMenuMapping;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField1GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField1GroupbBox.Buttons1Box;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField1GroupbBox.Field1Field;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField2GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField2GroupbBox.Buttons2Box;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField2GroupbBox.Buttons2Box.ToggleStatusMenuButton;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField2GroupbBox.Field2Field;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField2GroupbBox.Field2Field.StyleMenu;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField3GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField3GroupbBox.Field3Field;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField4GroupbBox;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.FormFieldBox.FormField4GroupbBox.Field4Field;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.LongRunningOperationBox;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.LongRunningOperationBox.CancellationDurationField;
import org.eclipse.scout.widgets.client.ui.forms.FormFieldForm.MainBox.LongRunningOperationBox.StartLongRunningOperationButton;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractStatusButton;

@ClassId("cd2b4857-8c7f-49d9-8284-f000464c0212")
public class FormFieldForm extends AbstractForm implements IPageForm {

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "Form Field";
  }

  @Override
  protected void execInitForm() {
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public Buttons1Box getButtons1Box() {
    return getFieldByClass(Buttons1Box.class);
  }

  public Buttons2Box getButtons2Box() {
    return getFieldByClass(Buttons2Box.class);
  }

  public FormFieldBox getFormFieldBox() {
    return getFieldByClass(FormFieldBox.class);
  }

  public FormField1GroupbBox getFormField1GroupbBox() {
    return getFieldByClass(FormField1GroupbBox.class);
  }

  public Field1Field getField1Field() {
    return getFieldByClass(Field1Field.class);
  }

  public FormField2GroupbBox getFormField2GroupbBox() {
    return getFieldByClass(FormField2GroupbBox.class);
  }

  public Field2Field getField2Field() {
    return getFieldByClass(Field2Field.class);
  }

  public FormField3GroupbBox getFormField3GroupbBox() {
    return getFieldByClass(FormField3GroupbBox.class);
  }

  public Field3Field getField3Field() {
    return getFieldByClass(Field3Field.class);
  }

  public FormField4GroupbBox getFormField4GroupbBox() {
    return getFieldByClass(FormField4GroupbBox.class);
  }

  public Field4Field getField4Field() {
    return getFieldByClass(Field4Field.class);
  }

  public LongRunningOperationBox getLongRunningOperationBox() {
    return getFieldByClass(LongRunningOperationBox.class);
  }

  public CancellationDurationField getCancellationDurationField() {
    return getFieldByClass(CancellationDurationField.class);
  }

  public ToggleStatusMenuButton getToggleStatusMenuButton() {
    return getFieldByClass(ToggleStatusMenuButton.class);
  }

  public StartLongRunningOperationButton getStartLongRunningOperationButton() {
    return getFieldByClass(StartLongRunningOperationButton.class);
  }

  @Order(10)
  @ClassId("6bcbadc1-57ab-402f-b9f6-8023b89559ea")
  public class MainBox extends AbstractGroupBox {

    @Order(10)
    @ClassId("ca926aa7-fc47-4355-a084-312f04f5f73e")
    public class FormFieldBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Form fields";
      }

      @Order(10)
      @ClassId("46dfa6c8-62bf-46cf-99c3-b9f4a5bab00b")
      public class FormField1GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("82abc465-ec30-4a3d-b2e0-6a9528a9f993")
        public class Field1Field extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 1";
          }
        }

        @Order(15)
        @ClassId("7a7b1b6e-4048-4de0-acca-15f51fd3be25")
        public class Buttons1Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField1Field();
          }
        }
      }

      @Order(20)
      @ClassId("50135e8f-892d-4a57-ae86-3124208cc69c")
      public class FormField2GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("fe0e0043-e6f2-4226-9a7f-edc8aa7075f2")
        public class Field2Field extends AbstractIntegerField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 2";
          }

          @Order(1000)
          @ClassId("7bdb6588-cf86-43a7-8c19-e38b62ee5cd7")
          public class ShowValueMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DisplayValue");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.NotNull);
            }

            @Override
            protected void execAction() {
              MessageBoxes.createOk().withBody("Value is " + getValue()).show();
            }

          }

          @Order(2000)
          @ClassId("588010ff-502b-4e41-9d73-a351d25c6dbc")
          public class StyleMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("FieldStyle");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
            }

            @Order(1000)
            @ClassId("bc8efcd2-0a49-4e66-8860-d4bf3ec0574f")
            public class ClassicMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return "Classic";
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
              }

              @Override
              protected void execAction() {
                getField2Field().setFieldStyle(IFormField.FIELD_STYLE_CLASSIC);
              }
            }

            @Order(2000)
            @ClassId("2609c148-7c5e-4a1f-8e33-f2e4ae1371f1")
            public class AlternativeMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("Alternative");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(ValueFieldMenuType.NotNull, ValueFieldMenuType.Null);
              }

              @Override
              protected void execAction() {
                getField2Field().setFieldStyle(IFormField.FIELD_STYLE_ALTERNATIVE);
              }
            }
          }
        }

        @Order(20)
        @ClassId("566d0a7c-5df2-4385-9dc2-2e9b9302a89d")
        public class Buttons2Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField2Field();
          }

          @Order(4000)
          @ClassId("ef2bcc3d-498c-4bd6-90d1-97528a6d2c22")
          public class ToggleStatusMenuButton extends AbstractLinkButton {
            @Override
            protected String getConfiguredLabel() {
              return "Show menu in status";
            }

            @Override
            protected boolean getConfiguredProcessButton() {
              return false;
            }

            @Override
            protected String getConfiguredTooltipText() {
              return "Menus are not shown in the status tooltip by default. If you want them to be displayed, you can configure a status menu mapping. In this example the menu is mapped to the error status and therefore not shown on a warning or info.";
            }

            @Override
            protected boolean getConfiguredFillHorizontal() {
              return false;
            }

            @Override
            protected void execClickAction() {
              Field2Field field = getField2Field();
              if (field.getStatusMenuMappings().size() > 0) {
                field.setStatusMenuMappings(new ArrayList<IStatusMenuMapping>());
                setLabel("Show menu in status");
                return;
              }

              IStatusMenuMapping mapping = new StatusMenuMapping();
              mapping.setSeverities(Arrays.asList(Status.ERROR));
              mapping.setMenu(field.getMenuByClass(StyleMenu.class));
              field.setStatusMenuMappings(Arrays.asList(mapping));
              setLabel("Hide menu in status");
            }
          }
        }
      }

      @Order(30)
      @ClassId("f50679aa-2795-4ad0-a96d-5287e245e45a")
      public class FormField3GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("fe2a97d8-50cd-4210-b0a3-e72c65fa8afb")
        public class Field3Field extends AbstractDateField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 3";
          }
        }

        @Order(20)
        @ClassId("4f2f4eed-e991-4d63-b20a-757f08cfee40")
        public class Buttons3Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField3Field();
          }
        }
      }

      @Order(40)
      @ClassId("7d747003-7622-4269-84e8-471075d44abf")
      public class FormField4GroupbBox extends AbstractGroupBox {

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(10)
        @ClassId("d5cbb5e6-634b-48d0-817a-b9d68ccdcf91")
        public class Field4Field extends AbstractDateTimeField {

          @Override
          protected String getConfiguredLabel() {
            return "Field 4";
          }
        }

        @Order(20)
        @ClassId("41f9ea37-148d-4a14-bd8c-16fdca164f45")
        public class Buttons4Box extends AbstractFieldButtonsBox {
          @Override
          protected IFormField getField() {
            return getField4Field();
          }
        }
      }
    }

    @Order(20)
    @ClassId("041d2e85-47a9-44c6-9d62-f403031720ae")
    public class LongRunningOperationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Long running operation";
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 3;
      }

      @Order(10)
      @ClassId("1f992612-0c29-4b9e-a76c-e09510932103")
      public class CancellationDurationField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Cancellation duration [s]";
        }

        @Override
        protected void execInitField() {
          setValue(2);
        }
      }

      @Order(20)
      @ClassId("98b65fcd-feb6-436c-9d92-c92d4c49dd00")
      public class StartLongRunningOperationButton extends AbstractButton {

        @Override
        protected String getConfiguredLabel() {
          return "Start long running operation";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected void execClickAction() {
          int cancellationDuration = (getCancellationDurationField().getValue() != null ? getCancellationDurationField().getValue() : 0);

          // Long running operation + cancellation
          SleepUtil.sleepSafe(30 + cancellationDuration, TimeUnit.SECONDS);
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }
      }
    }

    @Order(40)
    @ClassId("fbbda578-41df-49c6-96c9-0605e59cc34e")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @ClassId("8701cebc-6086-445a-ab42-ac268461bbe3")
  @Order(15)
  public abstract static class AbstractFieldButtonsBox extends AbstractSequenceBox {
    @Override
    protected boolean getConfiguredAutoCheckFromTo() {
      return false;
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    protected abstract IFormField getField();

    @Order(20)
    @ClassId("2f72b9d1-382c-49b9-9893-9250362a48a4")
    public class HideFieldButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return "Hide field in 3s";
      }

      @Override
      protected int getConfiguredDisplayStyle() {
        return DISPLAY_STYLE_LINK;
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Override
      protected void execClickAction() {
        if (!getField().isVisible()) {
          getField().setVisible(true);
          setLabel(getConfiguredLabel());
          return;
        }
        ModelJobs.schedule(() -> {
          getField().setVisible(false);
          setLabel("Show field");
        }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
            .withExecutionTrigger(Jobs.newExecutionTrigger()
                .withStartIn(3, TimeUnit.SECONDS)));
      }
    }

    @Order(2000)
    @ClassId("4c0b9f32-6286-48eb-a2cc-4fa8fc0430b0")
    public class StatusButton extends AbstractStatusButton {

      @Override
      protected IFormField getField() {
        return AbstractFieldButtonsBox.this.getField();
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }
    }

    @Order(3000)
    @ClassId("dd5e9aec-c92c-4bae-b3d5-2062fe62abb6")
    public class TooltipButton extends AbstractLinkButton {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("ToggleTooltip");
      }

      @Override
      protected boolean getConfiguredProcessButton() {
        return false;
      }

      @Override
      protected void execClickAction() {
        String text = null;
        if (AbstractFieldButtonsBox.this.getField().getTooltipText() == null) {
          text = "Hello, I am a tooltip!";
        }
        AbstractFieldButtonsBox.this.getField().setTooltipText(text);
      }
    }
  }
}
