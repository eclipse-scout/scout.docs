package org.eclipse.scout.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.ContentBox.NewViewBox;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.ContentBox.NewViewBox.OpenFormBasedButton;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.ContentBox.NewViewBox.TitleField;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.ContentBox.NewViewBox.ViewIdSmartField;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.StyleBox;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.StyleBox.BenchVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.StyleBox.HeaderVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.MainBox.StyleBox.NavigationVisibleButton;
import org.eclipse.scout.widgets.client.ui.forms.FormLayoutForm.ViewIdLookupCall.ViewId;

/**
 * <h3>{@link FormLayoutForm}</h3>
 *
 * @author aho
 */
public class FormLayoutForm extends AbstractForm implements IAdvancedExampleForm {
  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public TitleField getTitleField() {
    return getFieldByClass(TitleField.class);
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

  public OpenFormBasedButton getOpenFormBasedButton() {
    return getFieldByClass(OpenFormBasedButton.class);
  }

  public ViewIdSmartField getViewIdSmartField() {
    return getFieldByClass(ViewIdSmartField.class);
  }

  public NewViewBox getNewViewBox() {
    return getFieldByClass(NewViewBox.class);
  }

  public NavigationVisibleButton getNavigationVisibleButton() {
    return getFieldByClass(NavigationVisibleButton.class);
  }

  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(40)
    public class ContentBox extends AbstractGroupBox {

//      @Override
//      protected int getConfiguredGridColumnCount() {
//        return 2;
//      }

      @Order(-1000)
      public class NewViewBox extends AbstractSequenceBox {

        @Override
        protected boolean getConfiguredAutoCheckFromTo() {
          return false;
        }

        @Order(10)
        public class TitleField extends AbstractStringField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("Title");
          }

          @Override
          protected void execInitField() {
            setValue("Foo Bar");
          }
        }

        @Order(1005)
        public class ViewIdSmartField extends AbstractSmartField<ViewId> {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ViewID");
          }

          @Override
          protected void execInitField() {
            setValue(ViewId.South);
          }

          @Override
          protected Class<? extends ILookupCall<ViewId>> getConfiguredLookupCall() {
            return ViewIdLookupCall.class;
          }
        }

        @Order(2000)
        public class OpenFormBasedButton extends AbstractButton {
          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("OpenFormBased");
          }

          @Override
          protected void execClickAction() {
            ViewId viewId = getViewIdSmartField().getValue();
            if (viewId == null) {
              viewId = ViewId.South;
            }

            FormLayoutForm form = new FormLayoutForm();
            form.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
            form.setDisplayViewId(viewId.getValue());

            form.setTitle(getTitleField().getValue());
            form.setSubTitle(TEXTS.get("ViewID") + ": " + form.getDisplayViewId());
            form.getTitleField().setValue(null);

            form.startPageForm();
            form.getViewIdSmartField().setValue(viewId);
          }
        }
      }

    }

    @Order(2000)
    @ClassId("05cdb9cf-1c45-4f3c-9463-5b9f160b50c0")
    public class StyleBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("DisplayStyle");
      }

      @Order(1000)
      @ClassId("222b4c59-000d-4798-a96b-0da35e2b7734")
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

      @Order(2000)
      @ClassId("74182203-c9a0-43c1-b47b-b02d46f039c2")
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
      @ClassId("759b00bb-0427-4ec2-84f1-935299f60ead")
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

    @Order(10)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  @ApplicationScoped
  public static class ViewIdLookupCall extends LocalLookupCall<ViewId> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<ViewId>> execCreateLookupRows() {
      List<LookupRow<ViewId>> rows = new ArrayList<>();
      for (ViewId displayHint : ViewId.values()) {
        rows.add(new LookupRow<>(displayHint, displayHint.name()));
      }
      return rows;
    }

    public static enum ViewId {

      North(IForm.VIEW_ID_N),
      South(IForm.VIEW_ID_S),
      East(IForm.VIEW_ID_E),
      West(IForm.VIEW_ID_W),
      Center(IForm.VIEW_ID_CENTER);

      String m_value;

      private ViewId(String value) {
        m_value = value;
      }

      public String getValue() {
        return m_value;
      }
    }
  }
}
