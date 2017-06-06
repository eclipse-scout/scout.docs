package org.eclipse.scout.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
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
    else if (getText() != null) {
      addSubTreeRec(0, rows, null, true);
    }
    else {
      // all: when lookup is not 'load incremental', then load root nodes with child nodes
      // until level 3, otherwise only load root nodes
      addSubTreeRec(0, rows, null, !isLoadIncremental());
    }
    return sortResults(rows);
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<Long>> getDataByText() {
    List<LookupRow<Long>> rows = execCreateLookupRows();
    return filterByText(rows, getText());
  }

  private List<LookupRow<Long>> sortResults(List<LookupRow<Long>> lookupRows) {
    Collections.sort(lookupRows, new Comparator<LookupRow<Long>>() {
      @Override
      public int compare(LookupRow<Long> r1, LookupRow<Long> r2) {
        P_Data d1 = (P_Data) r1.getAdditionalTableRowData();
        P_Data d2 = (P_Data) r2.getAdditionalTableRowData();
        if (d1.getDepth() == d2.getDepth()) {
          return (int) (d1.getKey() - d2.getKey());
        }
        return d1.getDepth() - d2.getDepth();
      }
    });
    return lookupRows;
  }

  private List<LookupRow<Long>> filterByText(List<LookupRow<Long>> rows, String text) {
    // make a map to access all rows by key
    Map<Long, LookupRow<Long>> rowMap = new TreeMap<Long, LookupRow<Long>>();
    for (LookupRow<Long> row : rows) {
      rowMap.put(row.getKey(), row);
    }

    // create a set with the results that match the search text
    Set<LookupRow<Long>> results = new HashSet<LookupRow<Long>>();
    Pattern pattern = createSearchPattern(text);
    for (LookupRow<Long> row : rows) {
      if (row.getText() != null && pattern.matcher(row.getText().toLowerCase()).matches()) {
        results.add(row);
        // info: we don't have to deal with parent nodes. This is done the LocalLookupCall impl. anyway
        // also put all parent nodes of the row to the result
        while (row != null) {
          Long parentKey = row.getParentKey();
          LookupRow<Long> parentNode = null;
          if (parentKey != null) {
            parentNode = rowMap.get(parentKey);
            if (parentNode != null) {
              results.add(parentNode);
            }
          }
          row = parentNode;
        }
      }
    }

    return sortResults(new ArrayList<LookupRow<Long>>(results));
  }

  private int getDepth(long key) {
    return (int) Math.floor(Math.log10(key)); // count digits in the integer
  }

  private LookupRow<Long> newLookupRow(long key, int depth) {
    P_Data data = new P_Data(key, depth);
    LookupRow<Long> lookupRow = new LookupRow<Long>(key, "Node " + key + " (lv " + depth + ")");
    lookupRow.withAdditionalTableRowData(data);
    return lookupRow;
  }

  private void addSubTreeRec(int depth, List<LookupRow<Long>> rows, Long parentKey, boolean recursive) {
    for (int i = 1; i <= NODES_PER_LEVEL; i++) {
      long key = (parentKey == null ? 0 : parentKey.intValue()) * 10 + i;
      LookupRow<Long> row = newLookupRow(key, depth);
      rows.add(row);
      if (parentKey != null) {
        row.withParentKey(parentKey);
      }
      if (recursive && depth < MAX_DEPTH) {
        addSubTreeRec(depth + 1, rows, row.getKey(), recursive);
      }
    }
  }

  public void setLoadIncremental(boolean loadIncremental) {
    m_loadIncremental = loadIncremental;
  }

  public boolean isLoadIncremental() {
    return m_loadIncremental;
  }

  static class P_Data extends AbstractTableRowData {

    private static final long serialVersionUID = 1L;

    P_Data(long key, int depth) {
      setCustomValue("key", key);
      setCustomValue("depth", depth);
    }

    public long getKey() {
      return (Long) getCustomValue("key");
    }

    public int getDepth() {
      return (Integer) getCustomValue("depth");
    }

  }

}
