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
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.eclipse.scout.testing.client.IGuiMock;
import org.eclipsescout.demo.minifigcreator.client.Activator;
import org.eclipsescout.demo.minifigcreator.client.ui.forms.ServerForm;
import org.eclipsescout.demo.minifigcreator.client.uitest.AbstractMinifigTestWithGuiScript;
import org.eclipsescout.demo.minifigcreator.client.uitest.Parts;
import org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IServerProcessService;
import org.eclipsescout.demo.minifigcreator.shared.services.process.ServerFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.ServerFormData.Table;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import org.osgi.framework.ServiceRegistration;

/**
 * Test with GUI Script for {@link ServerForm}.
 */
public class ServerFormUiTest extends AbstractMinifigTestWithGuiScript {
  private IServerProcessService m_mockService = Mockito.mock(IServerProcessService.class);
  private List<ServiceRegistration> m_registeredServices;

  @Before
  public void setUp() {
    m_registeredServices = TestingUtility.registerServices(Activator.getDefault().getBundle(), 1000, m_mockService);
  }

  @After
  public void tearDown() {
    TestingUtility.unregisterServices(m_registeredServices);
  }

  @Override
  protected void runModel() throws Throwable {
    ServerForm form = createForm();
    form.startModify();
    form.waitFor();
    //add assertions
  }

  @Override
  protected void runGui(IGuiMock gui) throws Throwable {
    //add interaction
    gui.clickOnPushButton(TEXTS.get("OkButton"));
  }

  private ServerForm createForm() throws ProcessingException {
    ServerFormData loadFormData = new ServerFormData();
    loadFormData.getTable().clearRows();
    addRow(loadFormData, Parts.PART_HEAD_1, 2);
    addRow(loadFormData, Parts.PART_HEAD_2, 3);
    addRow(loadFormData, Parts.PART_HEAD_3, 2);
    addRow(loadFormData, Parts.PART_HEAD_4, 5);
    addRow(loadFormData, Parts.PART_LEGS_1, 4);
    addRow(loadFormData, Parts.PART_LEGS_2, 7);
    addRow(loadFormData, Parts.PART_LEGS_3, 5);
    addRow(loadFormData, Parts.PART_LEGS_4, 9);
    addRow(loadFormData, Parts.PART_LEGS_5, 8);
    addRow(loadFormData, Parts.PART_LEGS_6, 3);
    addRow(loadFormData, Parts.PART_LEGS_7, 9);
    addRow(loadFormData, Parts.PART_TORSO_1, 3);
    addRow(loadFormData, Parts.PART_TORSO_2, 5);
    addRow(loadFormData, Parts.PART_TORSO_3, 1);
    addRow(loadFormData, Parts.PART_TORSO_4, 9);

    Mockito.when(m_mockService.load(Mockito.any(ServerFormData.class))).thenReturn(loadFormData);
    Mockito.when(m_mockService.store(Mockito.any(ServerFormData.class))).thenReturn(loadFormData);

    ServerForm form = new ServerForm();
    return form;
  }

  private void addRow(ServerFormData formData, Part part, Integer quantity) {
    Table table = formData.getTable();
    int r = table.addRow();
    table.setPart(r, part);
    table.setType(r, part.getType().name());
    table.setName(r, part.getName());
    table.setQuantity(r, quantity);
  }

}
