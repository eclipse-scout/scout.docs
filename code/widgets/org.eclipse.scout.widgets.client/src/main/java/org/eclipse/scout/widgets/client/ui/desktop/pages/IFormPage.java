package org.eclipse.scout.widgets.client.ui.desktop.pages;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPageWithNodes;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;

public interface IFormPage extends IPageWithNodes {

  Class<? extends IPageForm> getFormType();

}
