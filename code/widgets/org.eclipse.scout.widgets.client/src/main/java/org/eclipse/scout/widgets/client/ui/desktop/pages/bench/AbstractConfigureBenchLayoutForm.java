package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.math.BigDecimal;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.htmlfield.AbstractHtmlField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.client.services.lookup.DisplayViewIdLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.DisplayViewIdLookupCall.DisplayViewId;
import org.eclipse.scout.widgets.client.ui.desktop.pages.bench.AbstractConfigureBenchLayoutForm.MainBox.GrowField;
import org.eclipse.scout.widgets.client.ui.desktop.pages.bench.AbstractConfigureBenchLayoutForm.MainBox.HeaderField;
import org.eclipse.scout.widgets.client.ui.desktop.pages.bench.AbstractConfigureBenchLayoutForm.MainBox.InitialSizeField;
import org.eclipse.scout.widgets.client.ui.desktop.pages.bench.AbstractConfigureBenchLayoutForm.MainBox.RelativeField;
import org.eclipse.scout.widgets.client.ui.desktop.pages.bench.AbstractConfigureBenchLayoutForm.MainBox.ShrinkField;

@ClassId("e967058f-77ce-4b4e-b843-55f331139417")
public abstract class AbstractConfigureBenchLayoutForm<T extends FlexboxLayoutData> extends AbstractForm {

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_CENTER;
  }

  @Override
  protected String getConfiguredCssClass() {
    return "bench-layout-form";
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  protected int getCongfiguredMainBoxGridColumnCount() {
    return 1;
  }

  private String getViewTitle() {
    DisplayViewId viewId = DisplayViewIdLookupCall.getViewId(AbstractConfigureBenchLayoutForm.this.getDisplayViewId());
    return "View " + viewId;
  }

  protected abstract T getLayoutDataForUpdate();

  protected abstract void storeLayoutData(T layoutData);

  public HeaderField getHeaderField() {
    return getFieldByClass(HeaderField.class);
  }

  public AbstractConfigureBenchLayoutForm<?>.MainBox.ShrinkField getShrinkField() {
    return getFieldByClass(ShrinkField.class);
  }

  public AbstractConfigureBenchLayoutForm<?>.MainBox.GrowField getGrowField() {
    return getFieldByClass(GrowField.class);
  }

  public AbstractConfigureBenchLayoutForm<?>.MainBox.RelativeField getRelativeField() {
    return getFieldByClass(RelativeField.class);
  }

  public InitialSizeField getInitialSizeField() {
    return getFieldByClass(InitialSizeField.class);
  }

  @Order(1000)
  @ClassId("31b209be-a4d9-4a1f-9717-45d7ab4cc8a3")
  public class MainBox extends AbstractGroupBox {
    @Override
    protected String getConfiguredBorderDecoration() {
      return BORDER_DECORATION_EMPTY;
    }

    @Override
    protected int getConfiguredGridColumnCount() {
      return getCongfiguredMainBoxGridColumnCount();
    }

    @Order(1000)
    @ClassId("00673e3f-cfbf-4ccf-ae15-78f56c85c98b")
    public class HeaderField extends AbstractHtmlField {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected int getConfiguredGridW() {
        return getCongfiguredMainBoxGridColumnCount();
      }

      @Override
      protected int getConfiguredGridH() {
        return 2;
      }

      @Override
      protected double getConfiguredGridWeightY() {
        return 0;
      }

      @Override
      protected void execInitField() {
        StringBuilder content = new StringBuilder();
        DisplayViewId viewId = DisplayViewIdLookupCall.getViewId(AbstractConfigureBenchLayoutForm.this.getDisplayViewId());
        content.append(HTML.div(getViewTitle()).cssClass("title").toHtml());
        content.append(HTML.div("Change the bench layout configuration for the " + viewId + " below.").cssClass("content").toHtml());
        setValue(content.toString());
      }
    }

    @Order(2000)
    @ClassId("d330184a-7b95-4483-88a4-a3a967c1037e")
    public class InitialSizeField extends AbstractBigDecimalField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("InitialSize");
      }

      @Override
      protected int getConfiguredFractionDigits() {
        return 2;
      }

      @Override
      protected void execInitField() {
        T ld = getLayoutDataForUpdate();
        if (ld == null) {
          setValue(BigDecimal.ZERO);
        }
        else {
          setValue(BigDecimal.valueOf(ld.getInitial()));
        }
      }

      @Override
      protected void execChangedValue() {
        T layoutDataForUpdate = getLayoutDataForUpdate();
        layoutDataForUpdate.withInitial(getValue().doubleValue());
        storeLayoutData(layoutDataForUpdate);
      }
    }

    @Order(3000)
    @ClassId("7e22f9d4-ce6f-4303-be3c-94f7898881de")
    public class RelativeField extends AbstractBooleanField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("RelativeSize");
      }

      @Override
      protected void execInitField() {
        T ld = getLayoutDataForUpdate();
        if (ld == null) {
          setValue(Boolean.FALSE);
        }
        else {
          setValue(ld.isRelative());
        }
      }

      @Override
      protected void execChangedValue() {
        T layoutDataForUpdate = getLayoutDataForUpdate();
        layoutDataForUpdate.withRelative(getValue());
        storeLayoutData(layoutDataForUpdate);
      }
    }

    @Order(4000)
    @ClassId("3224a58d-ac93-492b-9157-c756e8d874d5")
    public class GrowField extends AbstractBigDecimalField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("BenchLayoutGrow");
      }

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("BenchLayoutGrowDescription");
      }

      @Override
      protected BigDecimal getConfiguredMinValue() {
        return BigDecimal.valueOf(0);
      }

      @Override
      protected BigDecimal getConfiguredMaxValue() {
        return BigDecimal.valueOf(Double.MAX_VALUE);
      }

      @Override
      protected void execInitField() {
        T ld = getLayoutDataForUpdate();
        if (ld == null) {
          setValue(BigDecimal.ONE);
        }
        else {
          setValue(BigDecimal.valueOf(ld.getGrow()));
        }
      }

      @Override
      protected void execChangedValue() {
        T layoutDataForUpdate = getLayoutDataForUpdate();
        layoutDataForUpdate.withGrow(getValue().doubleValue());
        storeLayoutData(layoutDataForUpdate);
      }
    }

    @Order(5000)
    @ClassId("f6f35315-2c39-47de-88c3-e52e815500ab")
    public class ShrinkField extends AbstractBigDecimalField {
      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("BenchLayoutShrink");
      }

      @Override
      protected String getConfiguredTooltipText() {
        return TEXTS.get("BenchLayoutShrinkDescription");
      }

      @Override
      protected BigDecimal getConfiguredMinValue() {
        return BigDecimal.ZERO;
      }

      @Override
      protected BigDecimal getConfiguredMaxValue() {
        return BigDecimal.valueOf(Double.MAX_VALUE);
      }

      @Override
      protected void execInitField() {
        T ld = getLayoutDataForUpdate();
        if (ld == null) {
          setValue(BigDecimal.ONE);
        }
        else {
          setValue(BigDecimal.valueOf(ld.getShrink()));
        }
      }

      @Override
      protected void execChangedValue() {
        T layoutDataForUpdate = getLayoutDataForUpdate();
        layoutDataForUpdate.withShrink(getValue().doubleValue());
        storeLayoutData(layoutDataForUpdate);
      }
    }

  }
}
