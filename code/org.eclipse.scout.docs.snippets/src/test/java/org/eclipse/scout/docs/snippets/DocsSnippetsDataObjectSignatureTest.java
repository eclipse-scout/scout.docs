/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.docs.snippets;

import java.util.Set;

import org.eclipse.scout.rt.dataobject.testing.signature.AbstractDataObjectSignatureTest;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

//tag::class[]
public class DocsSnippetsDataObjectSignatureTest extends AbstractDataObjectSignatureTest {

  @Override
  protected String getFilenamePrefix() {
    return "docs-snippets";
  }

  @Override
  protected Set<String> getPackageNamePrefixes() {
    return CollectionUtility.hashSet("org.eclipse.scout.docs.snippets");
  }
}
//end::class[]
