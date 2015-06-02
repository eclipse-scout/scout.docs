/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.client.services.BuddyIconProviderService;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;

public class BuddyNodePage extends AbstractPageWithNodes {

  private String m_name;
  private ChatForm m_form;

  @Override
  protected boolean getConfiguredExpanded() {
    return true;
  }

  @Override
  protected String getConfiguredIconId() {
    return BuddyIconProviderService.BUDDY_ICON_PREFIX + getName();
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
  protected String getConfiguredTitle() {
    return getName();
  }

  @Override
  protected void execPageActivated() throws ProcessingException {
    super.execPageActivated();

    // after buddy page activation the buddy's chat history is displayed on the right side
    ChatForm chatForm = getChatForm();
    setDetailForm(chatForm);
    setDefaultFocus();
  }

  public ChatForm getChatForm() throws ProcessingException {
    if (m_form == null) {
      m_form = new ChatForm();
      m_form.setAutoAddRemoveOnDesktop(false);
      m_form.setUserName(ClientSession.get().getUserId());
      m_form.setBuddyName(getName());
      m_form.startNew();
    }
    return m_form;
  }

  public void setDefaultFocus() {
    new ClientJob("set focus to message field", ClientSession.get(), true) {
      @Override
      protected void runVoid(IProgressMonitor monitor) throws Throwable {
        getChatForm().getMessageField().requestFocus();
      }
    }.schedule(200);
  }

  @FormData
  public String getName() {
    return m_name;
  }

  @FormData
  public void setName(String name) {
    m_name = name;
  }
}
