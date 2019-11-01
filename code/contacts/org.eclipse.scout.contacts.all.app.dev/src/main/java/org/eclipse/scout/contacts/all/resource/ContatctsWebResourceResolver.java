/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.contacts.all.resource;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.Platform;
import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.util.FinalValue;
import org.eclipse.scout.rt.platform.util.LazyValue;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.ui.webresource.ClasspathWebResourceResolver;
import org.eclipse.scout.rt.shared.ui.webresource.FilesystemWebResourceResolver;
import org.eclipse.scout.rt.shared.ui.webresource.IWebResourceResolver;

@Replace
public class ContatctsWebResourceResolver extends FilesystemWebResourceResolver {

  private final Path m_cortexRoot;

  protected ContatctsWebResourceResolver() {
    m_cortexRoot = findCortexRoot();
  }

  @Override
  protected URL getResourceImpl(String resourcePath) {
    return toUrl(m_cortexRoot.resolve(resourcePath));
  }

  protected static Path findCortexRoot() {
    Path workingDir = Paths.get("").toAbsolutePath();
    return workingDir.getParent().resolve("org.eclipse.scout.contacts.ui.html").resolve(OUTPUT_FOLDER_NAME);
  }
}
