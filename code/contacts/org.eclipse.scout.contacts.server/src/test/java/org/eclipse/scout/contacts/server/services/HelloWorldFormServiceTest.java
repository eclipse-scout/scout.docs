package org.eclipse.scout.contacts.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.contacts.server.ServerSession;
import org.eclipse.scout.contacts.shared.dtos.HelloWorldFormData;
import org.eclipse.scout.contacts.shared.services.IHelloWorldFormService;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <h3>{@link HelloWorldFormServiceTest}</h3>
 *
 * @author dwi
 */
@RunWith(ServerTestRunner.class)
@RunWithSubject(HelloWorldFormServiceTest.SUBJECT_NAME)
@RunWithServerSession(ServerSession.class)
public class HelloWorldFormServiceTest {
  public static final String SUBJECT_NAME = "test_subject";

  @Test
  public void testMessageContainsSubjectName() throws ProcessingException {
    HelloWorldFormData input = new HelloWorldFormData();
    input = BEANS.get(IHelloWorldFormService.class).load(input);

    Assert.assertNotNull(input.getMessage());
    Assert.assertEquals("Hello " + SUBJECT_NAME + "!", input.getMessage().getValue());
  }
}
