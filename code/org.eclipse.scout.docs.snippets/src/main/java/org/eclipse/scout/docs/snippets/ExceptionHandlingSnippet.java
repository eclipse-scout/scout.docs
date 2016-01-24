package org.eclipse.scout.docs.snippets;

import java.security.AccessController;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.PlatformException;
import org.eclipse.scout.rt.platform.exception.PlatformExceptionTranslator;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public final class ExceptionHandlingSnippet {

  {
    // tag::PlatformException.constr[]
    Exception cause = new Exception();

    // Create a PlatformException with a message
    new PlatformException("Failed to persist data");

    // Create a PlatformException with a message and cause
    new PlatformException("Failed to persist data", cause);

    // Create a PlatformException with a message with formatting anchors
    new PlatformException("Failed to persist data [entity={}, id={}]", "person", 123);

    // Create a PlatformException with a message containing formatting anchors and a cause
    new PlatformException("Failed to persist data [entity={}, id={}]", "person", 123, cause);

    // Create a PlatformException with context information associated
    new PlatformException("Failed to persist data", cause)
        .withContextInfo("entity", "person")
        .withContextInfo("id", 123);
    // end::PlatformException.constr[]
  }

  {
    // tag::slf4Jlogging[]
    Exception e = new Exception();

    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    // Log a message
    logger.error("Failed to persist data");

    // Log a message with exception
    logger.error("Failed to persist data", e);

    // Log a message with formatting anchors
    logger.error("Failed to persist data [entity={}, id={}]", "person", 123);

    // Log a message and exception with a message containing formatting anchors
    logger.error("Failed to persist data [entity={}, id={}]", "person", 123, e);
    // end::slf4Jlogging[]
  }

  private void snippetPlatformExceptionTranslator() {
    // tag::platformExceptionTranslator.example[]
    try {
      // do something
    }
    catch (Exception e) {
      throw BEANS.get(PlatformExceptionTranslator.class).translate(e)
          .withContextInfo("cid", "12345")
          .withContextInfo("user", Subject.getSubject(AccessController.getContext()))
          .withContextInfo("job", IFuture.CURRENT.get());
    }
    // end::platformExceptionTranslator.example[]
  }
}
