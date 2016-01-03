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

import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.client.services.BuddyAvatarIconProviderService;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm;

public class BuddyNodePage extends AbstractPageWithNodes {

  private String m_name;
  private ChatForm m_form;

  @Override
  protected boolean getConfiguredExpanded() {
    return true;
  }

  @Override
  protected String getConfiguredIconId() {
    return BuddyAvatarIconProviderService.BUDDY_ICON_PREFIX + getName();
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

  public void setDefaultFocus() {
    ModelJobs.schedule(new IRunnable() {
      @Override
      public void run() throws Exception {
        getChatForm().getMessageField().requestFocus();
      }
    }, ModelJobs.newInput(ClientRunContexts.copyCurrent())
        .withExecutionTrigger(Jobs.newExecutionTrigger()
            .withStartIn(200, TimeUnit.MILLISECONDS)));
  }

  public ChatForm getChatForm() {
    if (m_form == null) {
      m_form = new ChatForm();
      m_form.setShowOnStart(false);
      m_form.setUserName(ClientSession.get().getUserId());
      m_form.setBuddyName(getName());
      m_form.startNew();
    }
    return m_form;
  }

  @Override
  protected void execPageActivated() {
    super.execPageActivated();

    // after buddy page activation the buddy's chat history is displayed on the right side
    ChatForm chatForm = getChatForm();
    setDetailForm(chatForm);
    setDefaultFocus();
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
