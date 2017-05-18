package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

@ClassId("eef35b00-5331-4006-b261-ef353a1d4f75")
public class HierarchicalLookupCall extends LocalLookupCall<Long> {

  private static final long serialVersionUID = 1L;

  private static final int NODES_PER_LEVEL = 5;
  private static final int MAX_DEPTH = 2;

  private boolean m_loadIncremental;

  @Override
  protected List<LookupRow<Long>> execCreateLookupRows() {
    List<LookupRow<Long>> rows = new ArrayList<LookupRow<Long>>();
    if (getKey() != null) {
      long key = getKey();
      rows.add(newLookupRow(key, getDepth(key)));
    }
    else if (getRec() != null) {
      long key = getRec();
      addSubTreeRec(getDepth(key) + 1, rows, key, false);
    }
    else {
      // all: when lookup is not 'load incremental', then load root nodes with child nodes
      // until level 3, otherwise only load root nodes
      addSubTreeRec(0, rows, null, !isLoadIncremental());
    }
    return rows;
  }

  private int getDepth(long key) {
    return (int) Math.floor(Math.log10(key)); // count digits in the integer
  }

  private LookupRow<Long> newLookupRow(long key, int depth) {
    return new LookupRow<Long>(key, "Node " + key + " (lv " + depth + ")");
  }

  private void addSubTreeRec(int depth, List<LookupRow<Long>> rows, Long parentKey, boolean recursive) {
    for (int i = 1; i <= NODES_PER_LEVEL; i++) {
      long key = (parentKey == null ? 0 : parentKey.intValue()) * 10 + i;
      LookupRow<Long> row = newLookupRow(key, depth);
      rows.add(row);
      if (recursive && depth < MAX_DEPTH) {
        addSubTreeRec(depth + 1, rows, row.getKey(), recursive);
      }
      if (parentKey != null) {
        row.withParentKey(parentKey);
      }
    }
  }

  public void setLoadIncremental(boolean loadIncremental) {
    m_loadIncremental = loadIncremental;
  }

  public boolean isLoadIncremental() {
    return m_loadIncremental;
  }

}
