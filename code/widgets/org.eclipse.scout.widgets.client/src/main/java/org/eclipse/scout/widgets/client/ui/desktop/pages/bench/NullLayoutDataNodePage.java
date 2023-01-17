/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("850d9375-2e4e-4735-8f6c-cbcab38bd16a")
public class NullLayoutDataNodePage extends AbstractBenchLayoutPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Null Layout";
  }

  @Override
  protected void execCreatePageForms(List<IForm> formList) {

    // west column
    NorthWestForm nwf = new NorthWestForm();
    nwf.setEnabledGranted(false);
    nwf.start();
    formList.add(nwf);
    WestForm wf = new WestForm();
    wf.setEnabledGranted(false);
    wf.start();
    formList.add(wf);
    SouthWestForm swf = new SouthWestForm();
    swf.setEnabledGranted(false);
    swf.start();
    formList.add(swf);

    // center column
    NorthForm nf = new NorthForm();
    nf.setEnabledGranted(false);
    nf.start();
    formList.add(nf);
    CenterForm cf = new CenterForm();
    cf.setEnabledGranted(false);
    cf.start();
    formList.add(cf);
    SouthForm sf = new SouthForm();
    sf.setEnabledGranted(false);
    sf.start();
    formList.add(sf);

    // east column
    NorthEastForm nef = new NorthEastForm();
    nef.setEnabledGranted(false);
    nef.start();
    formList.add(nef);
    EastForm ef = new EastForm();
    ef.setEnabledGranted(false);
    ef.start();
    formList.add(ef);
    SouthEastForm sef = new SouthEastForm();
    sef.setEnabledGranted(false);
    sef.start();
    formList.add(sef);

  }

  @ClassId("15cd2b6d-9d0d-4e31-ba32-665fd4ac09ef")
  private class NorthWestForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_NW;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getWest().withNorth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }

  }

  @ClassId("5d18c9e7-4593-4226-8b59-17d103ca5001")
  private class WestForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_W;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getWest().withCenter(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("9159e2a7-3f05-433a-822c-6e5132b40ddb")
  private class SouthWestForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_SW;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getWest().withSouth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("26e8e054-aac3-4fcb-9d74-e0c2f866c424")
  private class NorthForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_N;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withNorth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }

  }

  @ClassId("6e134c85-fd67-4d47-9245-d6307d4a3904")
  private class CenterForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_CENTER;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withCenter(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("2830da01-0272-43bc-8a4f-b17549b98e53")
  private class SouthForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_S;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withSouth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("1d54d2eb-4848-4656-9904-ffe0d2b76d1f")
  private class NorthEastForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_NE;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getEast().withNorth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }

  }

  @ClassId("2cd785c2-ef91-4b58-afe4-55d2bd1d6236")
  private class EastForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_E;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getEast().withCenter(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("b058c75e-c9f0-4f20-8722-1a7056a42d9e")
  private class SouthEastForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_SE;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return new FlexboxLayoutData();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getEast().withSouth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

}
