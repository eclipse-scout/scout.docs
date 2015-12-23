package org.eclipse.scout.heatmap.demo.client.helloworld;

import org.eclipse.scout.heatmap.demo.shared.helloworld.HelloWorldFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHelloWorldFormService;
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
 * <h3>{@link HelloWorldFormTest}</h3> Contains Tests for the {@link HelloWorldForm}.
 */
@RunWith(ClientTestRunner.class)
@RunWithSubject("anonymous")
@RunWithClientSession(TestEnvironmentClientSession.class)
public class HelloWorldFormTest {

  // Register a mock service for {@link IHelloWorldFormService}
  @BeanMock
  private IHelloWorldFormService m_mockSvc;

  /**
   * Return a reference {@link HelloWorldFormData} on method {@link IHelloWorldFormService#load(HelloWorldFormData)}.
   */
  @Before
  public void setup() {
    HelloWorldFormData result = new HelloWorldFormData();

    Mockito.when(m_mockSvc.load(Matchers.any(HelloWorldFormData.class))).thenReturn(result);
  }

  @Test
  public void testMessageFieldDisabled() {
    HelloWorldForm frm = new HelloWorldForm();
    Assert.assertEquals(17, frm.getHeatmapField().getViewParameter().getZoomFactor());
  }
}
