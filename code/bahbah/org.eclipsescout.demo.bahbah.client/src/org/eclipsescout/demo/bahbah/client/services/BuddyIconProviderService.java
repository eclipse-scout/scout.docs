/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.bahbah.client.services;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.services.common.icon.IconProviderService;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.shared.services.process.IIconProcessService;
import org.osgi.framework.ServiceRegistration;

/**
 * provider of buddy icons, extends IconProviderService from scout runtime client classes
 */
public class BuddyIconProviderService extends IconProviderService implements IBuddyIconProviderService {
  private static IScoutLogger logger = ScoutLogManager.getLogger(BuddyIconProviderService.class);

  public static final String BUDDY_ICON_PREFIX = "@@BUDDY_ICON@@_";
  public static final String OPT_BUDDY_ICON_SUFFIX = "_open";

  private ClientSession m_session;

  @Override
  public void initializeService(ServiceRegistration registration) {
    super.initializeService(registration);

    // remember the client session because the icon lookup (getIconSpec()) is invoked from the UI thread where no session is present.
    m_session = ClientSession.get();
  }

  @Override
  public IconSpec getIconSpec(String iconName) {
    if (iconName.startsWith(BUDDY_ICON_PREFIX)) {
      // it is a buddy icon
      if (iconName.endsWith(OPT_BUDDY_ICON_SUFFIX)) {
        // special case for tables: they may add a suffix for open tree nodes -> remove as we only have one icon for expanded & not expanded folders
        iconName = iconName.substring(0, iconName.length() - OPT_BUDDY_ICON_SUFFIX.length());
      }
      P_LoadDbIconJob job = new P_LoadDbIconJob(m_session, iconName.substring(BUDDY_ICON_PREFIX.length()));
      job.schedule();
      try {
        job.join();
      }
      catch (InterruptedException e1) {
        logger.warn("interrupted waiting on buddy icon load job. ", e1);
      }

      if (job.getIconSpec().getContent() == null) {
        // but the user has no icon uploaded yet
        return super.getIconSpec(BUDDY_DEFAULT_ICON);
      }
      else {
        // return the icon from the database
        return job.getIconSpec();
      }
    }
    else {
      return super.getIconSpec(iconName);
    }
  }

  private static class P_LoadDbIconJob extends ClientJob {
    private IconSpec m_result;
    private final String m_iconName;

    public P_LoadDbIconJob(ClientSession session, String iconName) {
      super("get buddy icon image", session, false, true);
      m_iconName = iconName;
    }

    @Override
    protected void runVoid(IProgressMonitor monitor) throws Throwable {
      try {
        byte[] data = SERVICES.getService(IIconProcessService.class).loadIcon(m_iconName);
        m_result = new IconSpec(m_iconName, data);
      }
      catch (ProcessingException e) {
        logger.error("unable to get buddy icon '" + m_iconName + "' from the database", e);
      }
    }

    public IconSpec getIconSpec() {
      return m_result;
    }
  }
}
