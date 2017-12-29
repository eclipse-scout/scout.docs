package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.services.lookup.DisplayViewIdLookupCall.DisplayViewId;

/**
 * @author Andreas Hoegger
 */
@ApplicationScoped
public class DisplayViewIdLookupCall extends LocalLookupCall<DisplayViewId> {

  private static final long serialVersionUID = 1L;

  public enum DisplayViewId {

    N(IForm.VIEW_ID_N),
    NE(IForm.VIEW_ID_NE),
    E(IForm.VIEW_ID_E),
    SE(IForm.VIEW_ID_SE),
    S(IForm.VIEW_ID_S),
    SW(IForm.VIEW_ID_SW),
    W(IForm.VIEW_ID_W),
    NW(IForm.VIEW_ID_NW),
    Center(IForm.VIEW_ID_CENTER, "C (Center)"),
    Outline(IForm.VIEW_ID_OUTLINE, "[Deprecated] Outline"),
    OutlineSelector(IForm.VIEW_ID_OUTLINE_SELECTOR, "[Deprecated] OutlineSelector"),
    Detail(IForm.VIEW_ID_PAGE_DETAIL, "[Deprecated] PageDetail"),
    Search(IForm.VIEW_ID_PAGE_SEARCH, "[Deprecated] PageSearch"),
    Table(IForm.VIEW_ID_PAGE_TABLE, "[Deprecated] PageTable");

    private final String m_value;
    private final String m_displayText;

    DisplayViewId(String value) {
      this(value, null);
    }

    DisplayViewId(String value, String displayText) {
      m_value = value;
      m_displayText = (displayText == null ? name() : displayText);
    }

    public String getValue() {
      return m_value;
    }

    public String getDisplayText() {
      return m_displayText;
    }
  }

  @Override
  protected List<? extends ILookupRow<DisplayViewId>> execCreateLookupRows() {
    List<LookupRow<DisplayViewId>> rows = new ArrayList<>();
    for (DisplayViewId displayViewId : DisplayViewId.values()) {
      rows.add(new LookupRow<>(displayViewId, displayViewId.getDisplayText()));
    }
    return rows;
  }

  public static DisplayViewId getViewId(String viewId) {
    for (DisplayViewId displayViewId : DisplayViewId.values()) {
      if (ObjectUtility.equals(displayViewId.getValue(), viewId)) {
        return displayViewId;
      }
    }
    return null;
  }

}
