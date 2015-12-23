package org.eclipse.scout.heatmap.demo.client.helloworld;

import org.eclipse.scout.heatmap.demo.shared.helloworld.HeatmapFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHeatmapFormService;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * <h3>{@link HeatmapFormTest}</h3> Contains Tests for the {@link HeatmapForm}.
 */
@RunWith(ClientTestRunner.class)
@RunWithSubject("anonymous")
@RunWithClientSession(TestEnvironmentClientSession.class)
public class HeatmapFormTest {

  // Register a mock service for {@link IHelloWorldFormService}
  @BeanMock
  private IHeatmapFormService m_mockSvc;

  /**
   * Return a reference {@link HeatmapFormData} on method {@link IHeatmapFormService#load(HeatmapFormData)}.
   */
  @Before
  public void setup() {
    HeatmapFormData result = new HeatmapFormData();

    Mockito.when(m_mockSvc.load(Matchers.any(HeatmapFormData.class))).thenReturn(result);
  }

  @Test
  public void testMessageFieldDisabled() {
    HeatmapForm frm = new HeatmapForm();
    Assert.assertEquals(17, frm.getHeatmapField().getViewParameter().getZoomFactor());
  }
}
