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
package org.eclipsescout.demo.minifigcreator.client.ui.forms;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.eclipse.scout.testing.client.ScoutClientAssert;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipsescout.demo.minifigcreator.client.Activator;
import org.eclipsescout.demo.minifigcreator.shared.services.process.DesktopFormData;
import org.eclipsescout.demo.minifigcreator.shared.services.process.FormState;
import org.eclipsescout.demo.minifigcreator.shared.services.process.IDesktopProcessService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.osgi.framework.ServiceRegistration;

/**
 * Tests for {@link org.eclipsescout.demo.minifigcreator.client.ui.forms.DesktopForm}
 *
 * @author jbr
 */
@RunWith(ScoutClientTestRunner.class)
public class DesktopFormTest {
  private IDesktopProcessService m_mockService = Mockito.mock(IDesktopProcessService.class);
  private List<ServiceRegistration> m_registeredServices;

  @Before
  public void setUp() {
    m_registeredServices = TestingUtility.registerServices(Activator.getDefault().getBundle(), 1000, m_mockService);
  }

  @After
  public void tearDown() {
    TestingUtility.unregisterServices(m_registeredServices);
  }

  /**
   * Test method: FormState tells that all fields should be enabled.
   */
  @Test
  public void testAllEnabled() throws Exception {
    DesktopForm form = createFormWithState(true, true, true, false);
    form.startView();

    ScoutClientAssert.assertEnabled(form.getHeadField());
    ScoutClientAssert.assertEnabled(form.getTorsoField());
    ScoutClientAssert.assertEnabled(form.getLegsField());
  }

  /**
   * Test method: FormState tells that all fields should be disabled.
   */
  @Test
  public void testAllDisabled() throws Exception {
    DesktopForm form = createFormWithState(false, false, false, false);
    form.startView();

    ScoutClientAssert.assertDisabled(form.getHeadField());
    ScoutClientAssert.assertDisabled(form.getTorsoField());
    ScoutClientAssert.assertDisabled(form.getLegsField());
  }

  /**
   * Test method: FormState tells that head field should be disabled.
   */
  @Test
  public void testHeadDisabled() throws Exception {
    DesktopForm form = createFormWithState(false, true, true, false);
    form.startView();

    ScoutClientAssert.assertDisabled(form.getHeadField());
    ScoutClientAssert.assertEnabled(form.getTorsoField());
    ScoutClientAssert.assertEnabled(form.getLegsField());
  }

  /**
   * Test method: FormState tells that torso field should be disabled.
   */
  @Test
  public void testTorsoDisabled() throws Exception {
    DesktopForm form = createFormWithState(true, false, true, false);
    form.startView();

    ScoutClientAssert.assertEnabled(form.getHeadField());
    ScoutClientAssert.assertDisabled(form.getTorsoField());
    ScoutClientAssert.assertEnabled(form.getLegsField());
  }

  /**
   * Test method: FormState tells that legs field should be disabled.
   */
  @Test
  public void testLegsDisabled() throws Exception {
    DesktopForm form = createFormWithState(true, true, false, false);
    form.startView();

    ScoutClientAssert.assertEnabled(form.getHeadField());
    ScoutClientAssert.assertEnabled(form.getTorsoField());
    ScoutClientAssert.assertDisabled(form.getLegsField());
  }

  /**
   * Ensure that all methods are called
   */
  @Test
  public void testAllMethodsAreCalled() throws Exception {
    DesktopForm form = createFormWithState(true, true, true, true);
    form.startView();

    InOrder orderCheck = Mockito.inOrder(form);

    Mockito.verify(form, Mockito.times(1)).updateImage();
    Mockito.verify(form, Mockito.times(1)).updateSummary();

    orderCheck.verify(form).updateImage();
    orderCheck.verify(form).updateSummary();
  }

  /**
   * Ensure the behavior is correct when the name is set in the {@link DesktopForm#getNameField()}
   */
  @Test
  public void testSetName() throws Exception {
    DesktopForm form = createFormWithState(true, true, true, true);
    form.startView();

    form.getNameField().setValue("Bob");

    Assert.assertEquals("Bob - value: 0", form.getSummaryField().getValue());

    Mockito.verify(form, Mockito.times(1)).updateImage();
    Mockito.verify(form, Mockito.times(2)).updateSummary();
  }

  private DesktopForm createFormWithState(final boolean headEnabled, final boolean torsoEnabled, final boolean legsEnabled, final boolean asSpy) throws ProcessingException {
    DesktopFormData loadFormData = new DesktopFormData();
    FormState state = new FormState();
    state.setHeadEnabled(headEnabled);
    state.setTorsoEnabled(torsoEnabled);
    state.setLegsEnabled(legsEnabled);

    loadFormData.setState(state);
    Mockito.when(m_mockService.load(Mockito.any(DesktopFormData.class))).thenReturn(loadFormData);

    if (asSpy) {
      P_DesktopForm form = Mockito.spy(new P_DesktopForm());
      form.doCallInitializer();
      return form;
    }
    else {
      DesktopForm form;
      form = new DesktopForm();
      return form;
    }
  }

  public static class P_DesktopForm extends DesktopForm {
    public P_DesktopForm() throws ProcessingException {
      super(false);
    }

    public void doCallInitializer() throws ProcessingException {
      super.callInitializer();
    }
  }
}
