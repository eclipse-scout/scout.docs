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
package org.eclipsescout.demo.widgets.client.util;

import java.net.URL;

import org.eclipse.scout.commons.StringUtility;
import org.eclipsescout.demo.widgets.client.Activator;
import org.osgi.framework.Bundle;

/**
 *
 */
public class ResourceUtility {
  public static final String RESOURCE_BASE = "/resources/";
  public static final String RESOURCE_IMAGE = RESOURCE_BASE + "images/";
  public static final String RESOURCE_HTML = RESOURCE_BASE + "html/";
  public static final String RESOURCE_SVG = RESOURCE_BASE + "svg/";

  public static final String SCOUT_LOGO = "/resources/images/eclipse_scout_logo.png";
  public static final String SCOUT_LOGO_FILENAME = "eclipse_scout_logo.png";
  public static final String BIRD = "http://2.bp.blogspot.com/_LDF9z4ZzZHo/TQZI-CUPl2I/AAAAAAAAAfc/--DuSZRxywM/s1600/bird_1008.jpg";
  public static final String BIRD_OFFLINE = "/resources/images/bird.jpg";

  private static final int TYPE_IMAGE = 1;
  private static final int TYPE_HTML = 2;
  private static final int TYPE_SVG = 3;

  public static URL getImageResource(String name) {
    return getResource(name, TYPE_IMAGE);
  }

  public static URL getHtmlResource(String name) {
    return getResource(name, TYPE_HTML);
  }

  public static URL getSVGResource(String name) {
    return getResource(name, TYPE_SVG);
  }

  private static URL getResource(String name, int type) {
    if (StringUtility.isNullOrEmpty(name)) {
      return null;
    }

    Bundle bundle = Activator.getDefault().getBundle();

    switch (type) {
      case TYPE_IMAGE:
        return bundle.getResource(RESOURCE_IMAGE + name);
      case TYPE_HTML:
        return bundle.getResource(RESOURCE_HTML + name);
      case TYPE_SVG:
        return bundle.getResource(RESOURCE_SVG + name);
      default:
        return null;
    }
  }
}
