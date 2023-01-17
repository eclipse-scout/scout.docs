/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link AbstractEmailField}
 */
public class EmailFieldTest {
  private AbstractEmailField testField;

  @Before
  public void before() {
    testField = new AbstractEmailField() {
    };

  }

  @Test
  public void testNullEmail() {
    testField.setValue(null);
    assertTrue(testField.isContentValid());
  }

  @Test
  public void testEmptyEmail() {
    testField.setValue("");
    assertTrue(testField.isContentValid());
  }

  @Test
  public void testInvalidEmail() {
    testField.setValue("a");
    assertFalse(testField.isContentValid());
  }

  @Test
  public void testValidEmail() {
    testField.setValue("a@b.ch");
    assertTrue(testField.isContentValid());
  }

}
