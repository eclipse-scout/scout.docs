/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.all.resource;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.shared.ui.webresource.FilesystemWebResourceResolver;

@Replace
public class ContactsWebResourceResolver extends FilesystemWebResourceResolver {

  private final Path m_root;

  protected ContactsWebResourceResolver() {
    m_root = findRoot();
  }

  @Override
  protected Stream<URL> getResourceImpl(String resourcePath) {
    return resolveUrls(m_root, resourcePath);
  }

  protected static Path findRoot() {
    Path workingDir = Paths.get("").toAbsolutePath();
    return workingDir.getParent().resolve("org.eclipse.scout.contacts.ui.html").resolve(OUTPUT_FOLDER_NAME);
  }
}
