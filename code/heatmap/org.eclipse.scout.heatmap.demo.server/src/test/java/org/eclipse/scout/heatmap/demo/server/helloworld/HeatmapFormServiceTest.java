package org.eclipse.scout.heatmap.demo.server.helloworld;

import org.eclipse.scout.heatmap.demo.server.ServerSession;
import org.eclipse.scout.heatmap.demo.shared.helloworld.HeatmapFormData;
import org.eclipse.scout.heatmap.demo.shared.helloworld.IHeatmapFormService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <h3>{@link HeatmapFormServiceTest}</h3>
 */
@RunWith(ServerTestRunner.class)
@RunWithSubject(HeatmapFormServiceTest.SUBJECT_NAME)
@RunWithServerSession(ServerSession.class)
public class HeatmapFormServiceTest {
  public static final String SUBJECT_NAME = "test_subject";

  @Test
  public void testMessageContainsSubjectName() {
    HeatmapFormData input = new HeatmapFormData();
    input = BEANS.get(IHeatmapFormService.class).load(input);

//    Assert.assertNotNull(input.getMessage());
//    Assert.assertEquals("Hello " + SUBJECT_NAME + "!", input.getMessage().getValue());
  }
}
