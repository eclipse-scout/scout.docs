/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
