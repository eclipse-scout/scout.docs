package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("2eec722f-4096-49ba-8755-6efdc62ca0f5")
public class ConfigurableColumnBenchLayoutNodePage extends AbstractBenchLayoutPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Column Bench Layout";
  }

  @Override
  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return new BenchLayoutData()
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
    // east
    EastForm e = new EastForm();
    e.start();
    formList.add(e);
  }

  @ClassId("c90ea798-1efb-4685-8081-424b7ce5a073")
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

  @ClassId("803b6e0f-ee04-4be5-92f5-61e177e8895b")
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

  @ClassId("0e91bf8d-d634-465d-81e7-f4e0370d3046")
  public class EastForm extends AbstractConfigureBenchLayoutForm<BenchColumnData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_E;
    }

    @Override
    protected BenchColumnData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getEast().copy();
    }

    @Override
    protected void storeLayoutData(BenchColumnData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.withEast(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

}
