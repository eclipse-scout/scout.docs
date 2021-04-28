package org.eclipse.scout.docs.snippets;

import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;

/**
 * @author Andreas Hoegger
 */
public class BenchLayoutSnippet {

  @SuppressWarnings("null")
  void example() {
    IDesktop desktop = null;
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
