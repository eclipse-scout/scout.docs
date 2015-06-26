package org.eclipsescout.demo.minifigcreator.client.testapp.swing;

import java.awt.Frame;
import java.util.List;

import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.session.IClientSessionRegistryService;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.shared.services.common.code.ICodeService;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.eclipse.scout.rt.testing.ui.swing.JUnitSwingJob;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplication;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.rt.ui.swing.window.desktop.ISwingScoutRootFrame;
import org.eclipse.scout.service.SERVICES;
import org.eclipse.scout.testing.client.TestingClientSessionRegistryService;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipsescout.demo.minifigcreator.client.Activator;
import org.eclipsescout.demo.minifigcreator.client.ClientSession;
import org.eclipsescout.demo.minifigcreator.shared.services.process.DesktopFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IDesktopProcessService;
import org.mockito.Mockito;
import org.osgi.framework.ServiceRegistration;

import com.bsiag.scout.rt.ui.swing.rayo.RayoSwingEnvironment;

/**
 *
 */
public class SwingTestApplication extends AbstractSwingApplication {
  private static final int TEST_START_DELAY = 200;
  private static final int FRAME_HEIGHT = 600;
  private static final int FRAME_WIDTH = 800;
  private static final int FRAME_LOCATION_Y = 50;
  private static final int FRAME_LOCATION_X = 50;
  private TestingClientSessionRegistryService m_testingClientSessionRegistryService = null;
  private List<ServiceRegistration> m_registeredServices;

  @Override
  protected Object startInSubject(IApplicationContext context) throws Exception {
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
    m_testingClientSessionRegistryService = TestingClientSessionRegistryService.registerTestingClientSessionRegistryService();
    m_registeredServices = TestingUtility.registerServices(Activator.getDefault().getBundle(), 500, createCodeService(), createDesktopProcessService());

    new JUnitSwingJob(ClientSession.class).schedule(TEST_START_DELAY);

    return super.startInSubject(context);
  }

  /**
   * Returns a minimal {@link ICodeService} implementation in order to be able to start the application
   *
   * @return
   */
  private ICodeService createCodeService() {
    //This could be replaced by a client-only CodeService registration when Bug 444213 is implemented
    ICodeService mockService = Mockito.mock(ICodeService.class);
    return mockService;
  }

  /**
   * Returns a minimal {@link IDesktopProcessService} implementation to be able to open the desktop
   */
  private IDesktopProcessService createDesktopProcessService() throws ProcessingException {
    IDesktopProcessService mockService = Mockito.mock(IDesktopProcessService.class);
    Mockito.when(mockService.load(Mockito.any(DesktopFormData.class))).thenReturn(new DesktopFormData());
    return mockService;
  }

  @Override
  protected ISwingEnvironment createSwingEnvironment() {
    return new RayoSwingEnvironment() {
      @Override
      public ISwingScoutRootFrame createRootComposite(Frame rootFrame, IDesktop desktop) {
        ISwingScoutRootFrame ui = super.createRootComposite(rootFrame, desktop);
        setTestFrameBounds(ui);
        return ui;
      }
    };
  }

  /**
   * Tests expect that size
   */
  private void setTestFrameBounds(ISwingScoutRootFrame ui) {
    ui.getSwingFrame().setBounds(FRAME_LOCATION_X, FRAME_LOCATION_Y, FRAME_WIDTH, FRAME_HEIGHT);
  }

  @Override
  protected IClientSession getClientSession() {
    return SERVICES.getService(IClientSessionRegistryService.class).newClientSession(ClientSession.class, initUserAgent());
  }

  @Override
  public void stop() {
    super.stop();
    TestingClientSessionRegistryService.unregisterTestingClientSessionRegistryService(m_testingClientSessionRegistryService);
    TestingUtility.unregisterServices(m_registeredServices);
  }

}
