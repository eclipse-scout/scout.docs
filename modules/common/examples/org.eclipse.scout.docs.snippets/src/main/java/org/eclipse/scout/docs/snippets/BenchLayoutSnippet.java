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

import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.internal.VirtualDesktop;

/**
 * @author Andreas Hoegger
 */
public class BenchLayoutSnippet {

  void example() {
    IDesktop desktop = new VirtualDesktop();
    // tag::BenchLayoutSample[]
    desktop.setBenchLayoutData( // <1>
        new BenchLayoutData()
            .withCacheKey("a-cache-key") // <2>
            .withCenter( // <3>
                new BenchColumnData()
                    .withNorth(new FlexboxLayoutData().withGrow(0).withShrink(0).withInitial(280).withRelative(false)) // <4>
                    .withCenter(new FlexboxLayoutData()) // <5>
                    .withSouth(new FlexboxLayoutData().withShrink(0).withInitial(-1)))); // <6>
    // end::BenchLayoutSample[]
  }
}
