package org.eclipse.scout.widgets.heatmap.client.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.AbstractHeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.HeatPoint;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.IHeatmapListener;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.CenterXField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.CenterYField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.HeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.IntensityField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.XField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.YField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.TopBox.ZoomLevelField;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;

/**
 * <h3>{@link HeatmapFieldForm}</h3>
 */
@Order(100000.0)
public class HeatmapFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private P_ViewParameterListener m_viewParameterListener = new P_ViewParameterListener();

  public HeatmapFieldForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("HeatmapField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public TopBox getTopBox() {
    return getFieldByClass(TopBox.class);
  }

  public HeatmapField getHeatmapField() {
    return getFieldByClass(HeatmapField.class);
  }

  public CenterXField getCenterXField() {
    return getFieldByClass(CenterXField.class);
  }

  public CenterYField getCenterYField() {
    return getFieldByClass(CenterYField.class);
  }

  public ZoomLevelField getZoomLevelField() {
    return getFieldByClass(ZoomLevelField.class);
  }

  public XField getXField() {
    return getFieldByClass(XField.class);
  }

  public YField getYField() {
    return getFieldByClass(YField.class);
  }

  public IntensityField getIntensityField() {
    return getFieldByClass(IntensityField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @Override
  protected void execInitForm() {
    updateViewParamFields();
    getHeatmapField().addPropertyChangeListener(IHeatmapField.PROP_VIEW_PARAMETER, m_viewParameterListener);
    getHeatmapField().addHeatmapListener(new IHeatmapListener() {

      @Override
      public void mapClicked(MapPoint point) {
        Float intensity = 200000.0f;

        getXField().setValue(point.getX());
        getYField().setValue(point.getY());
        getIntensityField().setValue(BigDecimal.valueOf(intensity));

        getHeatmapField().addHeatPoint(new HeatPoint(point.getX(), point.getY(), intensity));
      }

      @Override
      public void heatPointsAdded(Collection<HeatPoint> points) {
      }

    });
  }

  private void updateViewParamFields() {
    HeatmapViewParameter viewParameter = getHeatmapField().getViewParameter();
    getCenterXField().setValue(viewParameter.getCenter().getX());
    getCenterYField().setValue(viewParameter.getCenter().getY());
    getZoomLevelField().setValue(viewParameter.getZoomFactor());
  }

  private final class P_ViewParameterListener implements PropertyChangeListener {

    private boolean m_enabled = true;

    public void setEnabled(boolean enabled) {
      m_enabled = enabled;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      if (m_enabled) {
        updateViewParamFields();
      }
    }
  }

  @Order(1000.0)
  public class MainBox extends AbstractGroupBox {

    @Order(1000.0)
    public class TopBox extends AbstractGroupBox {

      @Order(1000.0)
      public class HeatmapField extends AbstractHeatmapField {
        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected void execInitField() {
          resetHeatPoints();
        }

        private void resetHeatPoints() {
          List<HeatPoint> heatPoints = new ArrayList<>();
          Float intensity = 100000.0f;

          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.18360), BigDecimal.valueOf(8.51954), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.44996), BigDecimal.valueOf(8.29276), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(46.95217), BigDecimal.valueOf(7.43259), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(51.21540), BigDecimal.valueOf(6.75074), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(49.87163), BigDecimal.valueOf(8.63468), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(48.13750), BigDecimal.valueOf(11.57106), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.39153), BigDecimal.valueOf(8.51093), intensity));

          setHeatPoints(heatPoints);
        }

        @Override
        public HeatmapViewParameter getConfiguredViewParameter() {
          return new HeatmapViewParameter(
              new MapPoint(BigDecimal.valueOf(49.16015), BigDecimal.valueOf(7.87720)), 6);
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("HeatmapField");
        }

        public void reset() {
          setViewParameter(getConfiguredViewParameter());
          resetHeatPoints();
        }

      }

      @Order(2000.0)
      public class CenterXField extends AbstractBigDecimalField {

        @Override
        protected int getConfiguredMinFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredMaxFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredFractionDigits() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CenterX");
        }

        @Override
        protected void execChangedValue() {
          HeatmapViewParameter oldViewParameter = getHeatmapField().getViewParameter();
          try {
            m_viewParameterListener.setEnabled(false);
            MapPoint newCenter = new MapPoint(getValue(), oldViewParameter.getCenter().getY());
            getHeatmapField().setViewParameter(
                new HeatmapViewParameter(newCenter, oldViewParameter.getZoomFactor()));
          }
          finally {
            m_viewParameterListener.setEnabled(true);
          }
        }
      }

      @Order(3000.0)
      public class CenterYField extends AbstractBigDecimalField {

        @Override
        protected int getConfiguredMinFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredMaxFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredFractionDigits() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CenterY");
        }

        @Override
        protected void execChangedValue() {
          HeatmapViewParameter oldViewParameter = getHeatmapField().getViewParameter();
          try {
            m_viewParameterListener.setEnabled(false);
            MapPoint newCenter = new MapPoint(oldViewParameter.getCenter().getX(), getValue());
            getHeatmapField().setViewParameter(
                new HeatmapViewParameter(newCenter, oldViewParameter.getZoomFactor()));
          }
          finally {
            m_viewParameterListener.setEnabled(true);
          }
        }

      }

      @Order(4000.0)
      public class ZoomLevelField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("ZoomLevel");
        }

        @Override
        protected void execChangedValue() {
          HeatmapViewParameter oldViewParameter = getHeatmapField().getViewParameter();
          try {
            m_viewParameterListener.setEnabled(false);
            getHeatmapField()
                .setViewParameter(new HeatmapViewParameter(oldViewParameter.getCenter(), getValue()));
          }
          finally {
            m_viewParameterListener.setEnabled(true);
          }
        }

      }

      @Order(5000.0)
      public class XField extends AbstractBigDecimalField {

        @Override
        protected int getConfiguredMinFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredMaxFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredFractionDigits() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("X");
        }

      }

      @Order(6000.0)
      public class YField extends AbstractBigDecimalField {

        @Override
        protected int getConfiguredMinFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredMaxFractionDigits() {
          return 5;
        }

        @Override
        protected int getConfiguredFractionDigits() {
          return 5;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Y");
        }

      }

      @Order(7000.0)
      public class IntensityField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Intensity");
        }

      }
    }

    @Order(30)
    public class AddHeatPointMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("AddHeatPoint");
      }

      @Override
      protected void execAction() {
        BigDecimal x = getXField().getValue();
        BigDecimal y = getYField().getValue();
        BigDecimal intensity = getIntensityField().getValue();

        if (x != null && y != null && intensity != null) {
          HeatPoint heatPoint = new HeatPoint(x, y, intensity.floatValue());
          getHeatmapField().addHeatPoint(heatPoint);
        }
        else {
          MessageBoxes.createOk().withHeader(TEXTS.get("PleaseProvideCoordinatesAndIntensity")).show();
        }
      }
    }

    @Order(40)
    public class ResetMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Reset");
      }

      @Override
      protected void execAction() {
        getHeatmapField().reset();
      }
    }

    @Order(50)
    public class ClearMenu extends AbstractMenu {
      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Clear");
      }

      @Override
      protected void execAction() {
        getHeatmapField().setHeatPoints(Collections.<HeatPoint> emptyList());
      }
    }

    @Order(40)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
