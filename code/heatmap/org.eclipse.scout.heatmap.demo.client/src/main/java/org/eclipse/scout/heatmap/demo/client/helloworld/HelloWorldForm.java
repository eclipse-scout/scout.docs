package org.eclipse.scout.heatmap.demo.client.helloworld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.AbstractHeatmapField;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.HeatPoint;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.IHeatmapListener;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.CenterXField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.CenterYField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.HeatmapField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.IntensityField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.XField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.YField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.ZoomLevelField;
import org.eclipse.scout.heatmap.demo.shared.helloworld.HelloWorldFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHelloWorldFormService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * <h3>{@link HelloWorldForm}</h3>
 */
@FormData(value = HelloWorldFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class HelloWorldForm extends AbstractForm {

  private P_ViewParameterListener m_viewParameterListener = new P_ViewParameterListener();

  public HelloWorldForm() {
    super();
    setHandler(new ViewHandler());
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredModalityHint() {
    return MODALITY_HINT_MODELESS;
  }

  @Override
  protected void execInitForm() {
    updateViewParamFields();
    getHeatmapField().addPropertyChangeListener(IHeatmapField.PROP_VIEW_PARAMETER, m_viewParameterListener);
    getHeatmapField().addHeatmapListener(new IHeatmapListener() {

      @Override
      public void heatPointsAdded(Collection<HeatPoint> points) {
        Iterator<HeatPoint> iterator = points.iterator();
        if (iterator.hasNext()) {
          HeatPoint heatPoint = iterator.next();
          getXField().setValue(heatPoint.getX());
          getYField().setValue(heatPoint.getY());
          getIntensityField().setValue(BigDecimal.valueOf(heatPoint.getIntensity()));
        }
      }
    });
  }

  private void updateViewParamFields() {
    HeatmapViewParameter viewParameter = getHeatmapField().getViewParameter();
    getCenterXField().setValue(viewParameter.getCenter().getX());
    getCenterYField().setValue(viewParameter.getCenter().getY());
    getZoomLevelField().setValue(viewParameter.getZoomFactor());
  }

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.World;
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

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("MessageFromServer");
      }

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
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.3914), BigDecimal.valueOf(8.51180), 1.0f));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.39155), BigDecimal.valueOf(8.5119), 0.4f));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.3915), BigDecimal.valueOf(8.51185), 0.7f));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.3916), BigDecimal.valueOf(8.51170), 0.2f));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.391), BigDecimal.valueOf(8.511), 0.5f));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.390), BigDecimal.valueOf(8.511), 0.5f));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(47.390), BigDecimal.valueOf(8.511), 0.3f));
          setHeatPoints(heatPoints);
        }

        @Override
        public HeatmapViewParameter getConfiguredViewParameter() {
          return new HeatmapViewParameter(
              new MapPoint(BigDecimal.valueOf(47.39141), BigDecimal.valueOf(8.51180)), 17);
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Message");
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

    @Order(10)
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
          HeatPoint heatPoint = new HeatPoint(x, y,
              intensity.floatValue());
          getHeatmapField().addHeatPoint(heatPoint);
        }
        else {
          MessageBoxes.createOk().withHeader(TEXTS.get("PleaseProvideCoordinatesAndIntensity")).show();
        }

      }
    }

    @Order(20)
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

    @Order(30)
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
  }

  public class ViewHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() {
      IHelloWorldFormService service = BEANS.get(IHelloWorldFormService.class);
      HelloWorldFormData formData = new HelloWorldFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }
  }

}
