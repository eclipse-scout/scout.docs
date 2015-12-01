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

import org.eclipse.scout.rt.client.services.common.icon.IIconProviderService;
import org.eclipse.scout.rt.client.services.common.icon.IconLocator;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.shared.services.process.IIconProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(2000)
public class BuddyAvatarIconProviderService implements IIconProviderService {
  private static final Logger logger = LoggerFactory.getLogger(BuddyAvatarIconProviderService.class);

  /**
   * the default buddy icon used when the user has not uploaded an icon yet. icon must be located in client plugin under
   * resources/icons
   */
  public static final String BUDDY_DEFAULT_ICON = "default_buddy_icon";
  public static final String BUDDY_ICON_PREFIX = "@@BUDDY_ICON@@_";
  public static final String OPT_BUDDY_ICON_SUFFIX = "_open";

  public BuddyAvatarIconProviderService() {
  }

  @Override
  public IconSpec getIconSpec(String iconName) {
    if (iconName.startsWith(BUDDY_ICON_PREFIX)) {
      return getBuddyAvatarIconSpec(iconName);
    }
    return null;
  }

  protected IconSpec getBuddyAvatarIconSpec(String iconName) {
    // it is a buddy icon
    if (iconName.endsWith(OPT_BUDDY_ICON_SUFFIX)) {
      // special case for tables: they may add a suffix for open tree nodes -> remove as we only have one icon for expanded & not expanded folders
      iconName = iconName.substring(0, iconName.length() - OPT_BUDDY_ICON_SUFFIX.length());
    }

    IconSpec spec = loadBuddyAvatarIconSpec(iconName.substring(BUDDY_ICON_PREFIX.length()));
    if (spec.getContent() != null) {
      // return the icon from the database
      return spec;
    }
    else {
      // but the user has no icon uploaded yet
      return IconLocator.instance().getIconSpec(BUDDY_DEFAULT_ICON);
    }
  }

  protected IconSpec loadBuddyAvatarIconSpec(String m_iconName) {
    try {
      if (ClientSession.get() != null) {
        byte[] data = BEANS.get(IIconProcessService.class).loadIcon(m_iconName);
        return new IconSpec(m_iconName, data);
      }
    }
    catch (RuntimeException e) {
      logger.error("unable to get buddy icon '" + m_iconName + "' from the database", e);
    }
    return null;
  }

}
