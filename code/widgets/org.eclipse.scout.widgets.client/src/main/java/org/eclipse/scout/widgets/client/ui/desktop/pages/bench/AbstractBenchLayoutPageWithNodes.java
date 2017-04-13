package org.eclipse.scout.widgets.client.ui.desktop.pages.bench;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

/**
 * <h3>{@link AbstractBenchLayoutPageWithNodes}</h3>
 *
 * @author aho
 */
public class AbstractBenchLayoutPageWithNodes extends AbstractPageWithNodes {

  private List<IForm> m_pageForms;
  private BenchLayoutData m_benchLayoutData;

  @Override
  protected boolean getConfiguredDetailFormVisible() {
    return false;
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected boolean getConfiguredTableVisible() {
    return false;
  }

  @Override
  protected void execInitPage() {
    super.execInitPage();
    setBenchLayoutData(getConfiguredBenchLayoutData());
  }

  @Override
  protected void execPageActivated() {
    super.execPageActivated();
    IDesktop.CURRENT.get().setBenchLayoutData(getBenchLayoutData());
    List<IForm> formList = new ArrayList<>();
    execCreatePageForms(formList);
    m_pageForms = CollectionUtility.arrayList(formList);
  }

  @Override
  protected void execPageDeactivated() {
    // remove all page forms
    if (m_pageForms != null) {
      for (IForm f : m_pageForms) {
        f.doClose();
      }
    }
    super.execPageDeactivated();
  }

  protected void execCreatePageForms(List<IForm> formList) {
  }

  protected BenchLayoutData getConfiguredBenchLayoutData() {
    return null;
  }

  public BenchLayoutData getBenchLayoutData() {
    return m_benchLayoutData;
  }

  public void setBenchLayoutData(BenchLayoutData benchLayoutData) {
    m_benchLayoutData = benchLayoutData;
  }
}
