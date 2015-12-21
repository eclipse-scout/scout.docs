package org.eclipse.scout.heatmap.demo.client.helloworld;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.AbstractHeatmapField;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.CenterXField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.CenterYField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.TopBox.HeatmapField;
import org.eclipse.scout.heatmap.demo.client.helloworld.HelloWorldForm.MainBox.ZoomLevelField;
import org.eclipse.scout.heatmap.demo.shared.helloworld.HelloWorldFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHelloWorldFormService;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;

/**
 * <h3>{@link HelloWorldForm}</h3>
 *
 * @author asa
 */
@FormData(value = HelloWorldFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class HelloWorldForm extends AbstractForm {

  private P_ViewParameterListener m_viewParameterListener = new P_ViewParameterListener();;

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
        protected String getConfiguredLabel() {
          return TEXTS.get("Message");
        }

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
          getHeatmapField().setViewParameter(new HeatmapViewParameter(newCenter, oldViewParameter.getZoomFactor()));
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
          getHeatmapField().setViewParameter(new HeatmapViewParameter(newCenter, oldViewParameter.getZoomFactor()));
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
          getHeatmapField().setViewParameter(new HeatmapViewParameter(oldViewParameter.getCenter(), getValue()));
        }
        finally {
          m_viewParameterListener.setEnabled(true);
        }
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
