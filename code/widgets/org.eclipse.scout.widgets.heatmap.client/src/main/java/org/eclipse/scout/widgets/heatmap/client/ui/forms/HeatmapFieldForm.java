/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.heatmap.client.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.eclipse.scout.rt.client.ui.Coordinates;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.AbstractHeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.HeatPoint;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.HeatmapViewParameter;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.IHeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.IHeatmapListener;
import org.eclipse.scout.widgets.heatmap.client.ui.form.fields.heatmapfield.MapPoint;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.CenterXField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.CenterYField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.HeatmapField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.IntensityField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.XField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.YField;
import org.eclipse.scout.widgets.heatmap.client.ui.forms.HeatmapFieldForm.MainBox.GroupBox.ZoomLevelField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(100000.0)
@ClassId("f20c76db-bdbd-4560-8ba8-d77f47eaee01")
public class HeatmapFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final Logger LOG = LoggerFactory.getLogger(HeatmapFieldForm.class);

  private final P_ViewParameterListener m_viewParameterListener = new P_ViewParameterListener();

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

  public GroupBox getGroupBox() {
    return getFieldByClass(GroupBox.class);
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
        float intensity = 200000.0f;

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
  @ClassId("9080f47e-61b3-4fdb-9ce6-26d4f42c8e6e")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected void execInitField() {
      setStatusVisible(false);
    }

    @Order(1000.0)
    @ClassId("0970e519-5f3d-4bf3-b562-e7ab6b08cdf5")
    public class GroupBox extends AbstractGroupBox {

      @Order(1000.0)
      @ClassId("48147eca-c147-4656-831d-b86950243b95")
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

          heatPoints.add(new HeatPoint(BigDecimal.valueOf(8.51954), BigDecimal.valueOf(47.18360), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(8.29276), BigDecimal.valueOf(47.44996), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(7.43259), BigDecimal.valueOf(46.95217), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(6.75074), BigDecimal.valueOf(51.21540), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(8.63468), BigDecimal.valueOf(49.87163), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(11.57106), BigDecimal.valueOf(48.13750), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(8.51093), BigDecimal.valueOf(47.39153), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(10.034250), BigDecimal.valueOf(53.563472), intensity));
          heatPoints.add(new HeatPoint(BigDecimal.valueOf(-75.544536), BigDecimal.valueOf(39.169648), intensity));

          setHeatPoints(heatPoints);
        }

        @Override
        public HeatmapViewParameter getConfiguredViewParameter() {
          return new HeatmapViewParameter(new MapPoint(BigDecimal.valueOf(7.87720), BigDecimal.valueOf(49.16015)), 6);
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
      @ClassId("13c9c14f-3a5e-4e36-9998-6fd180c647f2")
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
      @ClassId("0ab234c6-bfe5-46f4-b4ad-effb758736ff")
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
      @ClassId("8fc03330-0d88-438a-9142-97c8b88b69b0")
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

      @Order(5000.0)
      @ClassId("d571e6db-d3c4-4d5c-9ac1-6066f3066e7b")
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
      @ClassId("a34e7dcb-fc64-4fc8-aaa3-18129ccfce13")
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
      @ClassId("26113ac3-8c32-4f9e-8e7a-33318baecb81")
      public class IntensityField extends AbstractBigDecimalField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Intensity");
        }
      }
    }

    @Order(30)
    @ClassId("3ba23ac4-1983-43ce-b614-635e9fe7d33b")
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
    @ClassId("f45afe7c-0ef8-4d75-8d82-b726771f632c")
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
    @ClassId("068844ef-53a1-4906-93c2-4dcffae2126c")
    public class ClearMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Clear");
      }

      @Override
      protected void execAction() {
        getHeatmapField().setHeatPoints(Collections.emptyList());
      }
    }

    @Order(60)
    @ClassId("dc57d776-8462-4636-960f-cac4358d06ab")
    public class ScrollToCurrentLocationMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ScrollToCurrentLocation");
      }

      @Override
      protected void execAction() {
        try {
          Future<Coordinates> future = getDesktop().requestGeolocation();
          Coordinates location = future.get();
          getCenterXField().setValue(location.getLongitudeAsBigDecimal());
          getCenterYField().setValue(location.getLatitudeAsBigDecimal());
          if (NumberUtility.nvl(getZoomLevelField().getValue(), 0) < 16) {
            getZoomLevelField().setValue(16);
          }
        }
        catch (InterruptedException | ExecutionException e) {
          LOG.warn("Error while getting location", e);
          MessageBoxes.createOk()
              .withSeverity(IStatus.ERROR)
              .withHeader(TEXTS.get("ErrorWhileGettingLocation"))
              .withBody(e.getMessage())
              .show();
        }
      }
    }

    @Order(70)
    @ClassId("807a002c-ea72-4b93-ad94-3d08dbadaab0")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
