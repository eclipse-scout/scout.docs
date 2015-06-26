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
package org.eclipsescout.demo.minifigcreator.client.uitest.forms;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.testing.shared.DevTestMarker;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.eclipse.scout.testing.client.IGuiMock;
import org.eclipse.scout.testing.client.IGuiMock.Key;
import org.eclipsescout.demo.minifigcreator.client.Activator;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm;
import org.eclipsescout.demo.minifigcreator.client.uitest.AbstractMinifigTestWithGuiScript;
import org.eclipsescout.demo.minifigcreator.client.uitest.Parts;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.IHeadLookupService;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.ILegsLookupService;
import org.eclipsescout.demo.minifigcreator.shared.services.lookup.ITorsoLookupService;
import org.eclipsescout.demo.minifigcreator.shared.services.process.DesktopFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.FormState;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IDesktopProcessService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.osgi.framework.ServiceRegistration;

/**
 * Test with GUI Script for {@link DesktopForm}.
 */
@DevTestMarker
public class DesktopFormUiTest extends AbstractMinifigTestWithGuiScript {
  private IDesktopProcessService m_mockService = Mockito.mock(IDesktopProcessService.class);
  private List<ServiceRegistration> m_registeredServices;

  @Before
  public void setUp() throws Exception {
    IHeadLookupService headLookupService = Mockito.mock(IHeadLookupService.class);
    Mockito.doReturn(Parts.toLookupRows(Parts.PART_HEAD_2)).when(headLookupService).getDataByText(Matchers.<ILookupCall<Part>> any());

    ITorsoLookupService torsoLookupService = Mockito.mock(ITorsoLookupService.class);
    Mockito.doReturn(Parts.toLookupRows(Parts.PART_TORSO_4, Parts.PART_TORSO_2, Parts.PART_TORSO_3, Parts.PART_TORSO_1)).when(torsoLookupService).getDataByAll(Matchers.<ILookupCall<Part>> any());

    ILegsLookupService legsLookupService = Mockito.mock(ILegsLookupService.class);
    Mockito.doReturn(Parts.toLookupRows(Parts.PART_LEGS_1)).when(legsLookupService).getDataByText(Matchers.<ILookupCall<Part>> any());

    m_registeredServices = TestingUtility.registerServices(Activator.getDefault().getBundle(), 1000, m_mockService, headLookupService, torsoLookupService, legsLookupService);
  }

  @After
  public void tearDown() {
    TestingUtility.unregisterServices(m_registeredServices);
  }

  @Override
  protected void runModel() throws Throwable {
    DesktopForm form = createForm();
    form.startView();
    form.waitFor(); // wait for runGui(..) to be finished
    Assert.assertEquals("Bob [Pirate, Scouty, Yellow] - value: 55", form.getSummaryField().getValue());
  }

  @Override
  protected void runGui(IGuiMock gui) throws Throwable {
    gui.typeText("Bob");
    gui.pressKey(Key.Tab);
    gui.typeText("Pirate");
    gui.pressKey(Key.Tab);
    gui.pressKey(Key.Down);
    gui.pressKey(Key.Down);
    gui.pressKey(Key.Enter);
    gui.pressKey(Key.Tab);
    gui.typeText("Y");
    gui.pressKey(Key.Enter);
    gui.pressKey(Key.Tab);
  }

  private DesktopForm createForm() throws ProcessingException {
    DesktopFormData loadFormData = new DesktopFormData();
    FormState state = new FormState();
    state.setHeadEnabled(true);
    state.setTorsoEnabled(true);
    state.setLegsEnabled(true);

    loadFormData.setState(state);
    Mockito.when(m_mockService.load(Mockito.any(DesktopFormData.class))).thenReturn(loadFormData);

    DesktopForm form = new DesktopForm();
    return form;
  }

}
