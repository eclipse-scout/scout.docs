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

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("39db31bc-25c4-41cc-bed6-1d9571172f90")
public class CachedBenchLayoutNodePage extends AbstractBenchLayoutPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Cached Bench Layout";
  }

  @Override
  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return new BenchLayoutData().withCacheKey(CachedBenchLayoutNodePage.class.getSimpleName())
        .withWest(new BenchColumnData())
        .withCenter(new BenchColumnData())
        .withEast(new BenchColumnData());
  }

  @Override
  protected void execCreatePageForms(List<IForm> formList) {
    super.execCreatePageForms(formList);
    // west form
    WestForm w = new WestForm();
    w.start();
    formList.add(w);

    // center form
    CenterForm c = new CenterForm();
    c.start();
    formList.add(c);
    // north east
    NorthEastForm ne = new NorthEastForm();
    ne.start();
    formList.add(ne);
    // east
    EastForm e = new EastForm();
    e.start();
    formList.add(e);
    // south east
    SouthEastForm se = new SouthEastForm();
    se.start();
    formList.add(se);
  }

  @ClassId("756427ea-651b-4d7e-b686-7eedf93aceaf")
  public class WestForm extends AbstractConfigureBenchLayoutForm<BenchColumnData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_W;
    }

    @Override
    protected BenchColumnData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getWest().copy();
    }

    @Override
    protected void storeLayoutData(BenchColumnData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.withWest(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }

  }

  @ClassId("813ae61b-aaa9-4b37-a55c-c5d8f65d0ebd")
  public class CenterForm extends AbstractConfigureBenchLayoutForm<BenchColumnData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_CENTER;
    }

    @Override
    protected BenchColumnData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getCenter().copy();
    }

    @Override
    protected void storeLayoutData(BenchColumnData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.withCenter(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("bca10c4f-ad8a-4687-813d-34df361bc8d7")
  public class NorthEastForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_NE;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getEast().getNorth().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getEast().withNorth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("2bb6ab60-74a3-4daf-b0e4-f5726eb13241")
  public class EastForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_E;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getEast().getCenter().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getEast().withCenter(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("7584b478-b2de-4860-82c3-d94a61506f7a")
  public class SouthEastForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_SE;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getEast().getSouth().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getEast().withSouth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

}
