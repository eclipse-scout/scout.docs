package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("29c3d138-cf65-4359-8a8b-0dda7cb48c37")
public class ConfigurableRowBenchLayoutNodePage extends AbstractBenchLayoutPageWithNodes {
  public static final String TITLE = "Row Bench Layout";

  @Override
  protected String getConfiguredTitle() {
    return TITLE;
  }

  @Override
  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return new BenchLayoutData()
        .withCenter(
            new BenchColumnData()
                .withTop(new FlexboxLayoutData())
                .withCenter(new FlexboxLayoutData())
                .withBottom(new FlexboxLayoutData()));
  }

  @Override
  protected void execCreatePageForms(List<IForm> formList) {

    // top form
    NorthForm top = new NorthForm();
    top.start();
    formList.add(top);

    // center form
    CenterForm center = new CenterForm();
    center.start();
    formList.add(center);
    // south
    SouthForm bottom = new SouthForm();
    bottom.start();
    formList.add(bottom);

  }

  @ClassId("b88bd47c-54b3-49cb-88e4-448b3c4dda12")
  public class NorthForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_N;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getCenter().getTop().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withTop(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }

  }

  @ClassId("824b1cca-c378-4980-be7f-06fa7cf6dc34")
  public class CenterForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_CENTER;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getCenter().getCenter().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withCenter(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

  @ClassId("3441dbef-e4d4-4bcb-a919-41fe955f8f53")
  public class SouthForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_S;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getCenter().getBottom().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withBottom(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }
  }

}
