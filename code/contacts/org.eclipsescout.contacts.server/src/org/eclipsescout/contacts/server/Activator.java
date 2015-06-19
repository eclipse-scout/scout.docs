package org.eclipsescout.contacts.server;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.scout.rt.server.scheduler.Scheduler;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

  public static String PLUGIN_ID = "org.eclipsescout.contacts.server";

  // The shared instance
  private static Activator plugin;

  /**
   * Returns the shared instance
   *
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  private Scheduler m_scheduler;

  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    if (m_scheduler != null) {
      m_scheduler.stop();
      m_scheduler = null;
    }
    plugin = null;
    super.stop(context);
  }

  public Scheduler getScheduler() {
    return m_scheduler;
  }

  public void setScheduler(Scheduler scheduler) {
    m_scheduler = scheduler;
  }
}
