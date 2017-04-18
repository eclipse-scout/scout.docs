package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.classid.ClassId;

@ClassId("79c3d957-ecf3-4b1f-9059-444758612221")
public class FixedTopNodePage extends AbstractBenchLayoutPageWithNodes {

  @Override
  protected String getConfiguredTitle() {
    return "Fixed North";
  }

  @Override
  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return new BenchLayoutData()
        .withCenter(
            new BenchColumnData()
                .withNorth(new FlexboxLayoutData().withInitial(-1).withGrow(0).withShrink(0))
                .withCenter(new FlexboxLayoutData()));

  }

  @Override
  protected void execCreatePageForms(List<IForm> formList) {

    // west form
    TopForm w1 = new TopForm();
    w1.setTitle("N 1");
    w1.setDisplayViewId(IForm.VIEW_ID_N);
    w1.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
    w1.setDisplayParent(getOutline());
    w1.start();
    formList.add(w1);
    // center form
    TopForm c1 = new TopForm();
    c1.setTitle("C 1");
    c1.setDisplayViewId(IForm.VIEW_ID_CENTER);
    c1.setDisplayHint(IForm.DISPLAY_HINT_VIEW);
    c1.setDisplayParent(getOutline());
    c1.start();
    formList.add(c1);

  }

  @ClassId("f7eb276e-25b2-4ec2-9072-c33d0e954bce")
  private class TopForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
    @Override
    protected String getConfiguredDisplayViewId() {
      return VIEW_ID_N;
    }

    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {
      return getDesktop().getBenchLayoutData().getCenter().getNorth().copy();
    }

    @Override
    protected void storeLayoutData(FlexboxLayoutData layoutData) {
      BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
      benchLayoutData.getCenter().withNorth(layoutData);
      getDesktop().setBenchLayoutData(benchLayoutData);
    }

  }

  @ClassId("49ad0efa-6f20-442f-ac0e-ba25ef2740a8")
  private class CenterForm extends AbstractConfigureBenchLayoutForm<FlexboxLayoutData> {
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

}
