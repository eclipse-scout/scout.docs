package org.eclipse.scout.widgets.client.services.lookup;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.scout.rt.client.ui.group.IGroup;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("988bc56a-343a-4842-a0b2-110734072c95")
public class CollapseStyleLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    return Stream
        .of(IGroup.COLLAPSE_STYLE_LEFT, IGroup.COLLAPSE_STYLE_RIGHT, IGroup.COLLAPSE_STYLE_BOTTOM)
        .map(s -> new LookupRow<>(s, s))
        .collect(Collectors.toList());
  }
}
