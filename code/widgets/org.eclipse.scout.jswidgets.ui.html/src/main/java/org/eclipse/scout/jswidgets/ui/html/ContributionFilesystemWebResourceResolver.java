/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.jswidgets.ui.html;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.shared.ui.webresource.FilesystemWebResourceResolver;

@Replace
public class ContributionFilesystemWebResourceResolver extends FilesystemWebResourceResolver {

  private final List<Path> m_stepRoots;

  protected ContributionFilesystemWebResourceResolver() {
    // <customized>
    List<Path> stepRoots = new ArrayList<>();
    stepRoots.addAll(findStepRoots());
    m_stepRoots = Collections.unmodifiableList(stepRoots);
  }

  @Override
  protected Stream<URL> getResourceImpl(String resourcePath) {
    // no super call. there is no content anyway. search in step modules instead
    return resolveUrls(m_stepRoots, resourcePath);
  }

  protected static List<Path> findStepRoots() {
    Path workingDir = Paths.get("").toAbsolutePath();
    Path workspaceDir = workingDir.getParent();
    try (Stream<Path> studioModules = Files.list(workspaceDir)) {
      return studioModules
          .filter(p -> "org.eclipse.scout.jswidgets.ui.html".equals(p.getFileName().toString()) || p.getFileName().toString().startsWith("org.eclipse.scout.jswidgets.ui.html."))
          .map(moduleRoot -> moduleRoot.resolve(OUTPUT_FOLDER_NAME))
          .collect(Collectors.toList());
    }
    catch (IOException e) {
      throw new ProcessingException("Exception building studio step roots for development.", e);
    }
  }
}
