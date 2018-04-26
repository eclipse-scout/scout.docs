package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.text.TEXTS;

@ClassId("b345f880-f807-4455-a22a-3c1e8cfd4c9a")
public class FixedFooterNodePage extends AbstractBenchLayoutPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Fixed footer";
  }

  @Override
  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return new BenchLayoutData()
        .withCenter(new BenchColumnData()
            .withCenter(new FlexboxLayoutData())
            .withSouth(new FlexboxLayoutData().withRelative(false).withInitial(-1).withGrow(0).withShrink(0)));
  }

  @Override
  protected void execCreatePageForms(List<IForm> formList) {
    CenterForm centerForm = new CenterForm();
    centerForm.start();
    formList.add(centerForm);
    FooterForm footerForm = new FooterForm();
    footerForm.start();
    formList.add(footerForm);
  }

  @ClassId("f8a1780a-4bbe-4169-8610-6817f37ea363")
  private static class CenterForm extends AbstractForm {
    @Override
    protected int getConfiguredDisplayHint() {
      return DISPLAY_HINT_VIEW;
    }

    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_CENTER;
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
      return false;
    }

    @ClassId("52aa663a-ed2c-4c3e-8f7c-5939144e95ef")
    @Order(1000)
    public class MainBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return BORDER_DECORATION_EMPTY;
      }

      @Order(1000)
      @ClassId("6500e8c8-8ddd-443f-850f-93bee34cbad8")
      public class NameField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Name");
        }

        @Override
        protected int getConfiguredMaxLength() {
          return 128;
        }
      }

      @Order(2000)
      @ClassId("bafd8a5d-8981-43c7-8405-8c2e86637cb4")
      public class OpenFooterFormAsDialogButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Open Footerform as Dialog";
        }

        @Override
        protected void execClickAction() {
          FooterForm form = new FooterForm();
          form.setDisplayHint(IForm.DISPLAY_HINT_DIALOG);
          form.setClosable(true);
          form.setDisplayParent(getDesktop());
          form.start();
        }
      }

    }
  }

  @ClassId("3f117d0a-84f5-4b1f-84db-179f06dc2cab")
  private static class FooterForm extends AbstractForm {

    @Override
    protected int getConfiguredDisplayHint() {
      return DISPLAY_HINT_VIEW;
    }

    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_S;
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
      return false;
    }

    @Override
    protected String getConfiguredCssClass() {
      return "footer-form";
    }

    @Order(1000)
    @ClassId("b2cfa0eb-f1a4-4dc6-a399-4a1b15c42785")
    public class MainBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredBorderDecoration() {
        return BORDER_DECORATION_EMPTY;
      }

      @Override
      protected int getConfiguredGridColumnCount() {
        return 1;
      }

      @Order(1000)
      @ClassId("4acebec8-eedd-42a8-b916-da966739b00d")
      public class ContentField extends AbstractHtmlField {
        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected double getConfiguredGridWeightY() {
          return 1;
        }

        @Override
        protected int getConfiguredGridW() {
          return super.getConfiguredGridW();
        }

        @Override
        protected void execInitField() {
          StringBuilder content = new StringBuilder();
          content.append(HTML.div("This is a footer").cssClass("title").toHtml());
          content.append(HTML.div("The footer is basically a form with a 'DISPLAY_HINT_VIEW' and placed in 'VIEW_ID_S'. The bench layout data (IDesktop.setBenchLayoutData()) has a fixed Center-South part.").cssClass("detail").toHtml());
          setValue(content.toString());
        }
      }

    }
  }
}
