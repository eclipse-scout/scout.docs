package org.eclipse.scout.contacts.client.template;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.scout.contacts.client.template.AbstractEmailField;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link AbstractEmailField}
 */
public class EmailFieldTest {
  private AbstractEmailField m_testField;

  @Before
  public void before() {
    m_testField = new AbstractEmailField() {
    };

  }

  @Test
  public void testNullEmail() {
    m_testField.setValue(null);
    assertTrue(m_testField.isContentValid());
  }

  @Test
  public void testEmptyEmail() {
    m_testField.setValue("");
    assertTrue(m_testField.isContentValid());
  }

  @Test
  public void testInvalidEmail() {
    m_testField.setValue("a");
    assertFalse(m_testField.isContentValid());
  }

  @Test
  public void testValidEmail() {
    m_testField.setValue("a@b.ch");
    assertTrue(m_testField.isContentValid());
  }

}
