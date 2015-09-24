package org.eclipse.scout.contacts.client.forms;

import java.util.Arrays;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.client.forms.HelloWorldForm.MainBox.TopBox.MessageField;
import org.eclipse.scout.contacts.shared.dtos.HelloWorldFormData;
import org.eclipse.scout.contacts.shared.services.IHelloWorldFormService;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.platform.BeanMetaData;
import org.eclipse.scout.rt.platform.IBean;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.shared.TestingUtility;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * <h3>{@link HelloWorldFormTest}</h3>
 * Contains Tests for the {@link HelloWorldForm}.
 *
 * @author dwi
 */
@RunWith(ClientTestRunner.class)
@RunWithSubject("anonymous")
@RunWithClientSession(TestEnvironmentClientSession.class)
public class HelloWorldFormTest {

  private static final String MESSAGE_VALUE = "testData";

  private static IBean<?> svcBean;

  /**
   * Registers an {@link IHelloWorldFormService} mock returning a reference {@link HelloWorldFormData} on method
   * {@link IHelloWorldFormService#load(HelloWorldFormData)}.
   *
   * @throws ProcessingException
   */
  @BeforeClass
  public static void setup() throws ProcessingException {
    HelloWorldFormData result = new HelloWorldFormData();
    result.getMessage().setValue(MESSAGE_VALUE);

    IHelloWorldFormService hwfs = Mockito.mock(IHelloWorldFormService.class);
    Mockito.when(hwfs.load(Matchers.any(HelloWorldFormData.class))).thenReturn(result);

    // register mock service in the global bean manager
    svcBean = TestingUtility.registerBean(
        new BeanMetaData(IHelloWorldFormService.class)
            .withInitialInstance(hwfs) // bean should use our mock instance
            .withoutAnnotation(TunnelToServer.class)); // remove TunnelToServer annotation that exists in IHelloWorldFormService so that no proxy to the server is created.
  }

  /**
   * unregister the mock again
   */
  @AfterClass
  public static void cleanup() {
    TestingUtility.unregisterBeans(Arrays.asList(svcBean));
  }

  /**
   * Tests that the {@link MessageField} is disabled.
   *
   * @throws ProcessingException
   */
  @Test
  public void testMessageFieldDisabled() throws ProcessingException {
    HelloWorldForm frm = new HelloWorldForm();
    Assert.assertFalse(frm.getMessageField().isEnabled());
  }

  /**
   * Tests that the {@link MessageField} is correctly filled after start.
   *
   * @throws ProcessingException
   */
  @Test
  public void testMessageCorrectlyImported() throws ProcessingException {
    HelloWorldForm frm = new HelloWorldForm();
    frm.start();

    Assert.assertEquals(MESSAGE_VALUE, frm.getMessageField().getValue());
  }
}
