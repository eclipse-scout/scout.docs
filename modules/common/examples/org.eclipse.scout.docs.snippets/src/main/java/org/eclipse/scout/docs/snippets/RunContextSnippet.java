package org.eclipse.scout.docs.snippets;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;

public final class RunContextSnippet {

  void snippets() throws Exception {
    {
      // tag::RunContexts.empty[]
      Subject subject = new Subject(); // <1>
      subject.getPrincipals().add(new SimplePrincipal("john"));
      subject.setReadOnly();

      // <2>
      RunContexts.empty()
          .withSubject(subject)
          .withLocale(Locale.US)
          .run(() -> {
            // run some code <3>
            System.out.println(NlsLocale.CURRENT.get()); // > Locale.US
            System.out.println(Subject.getSubject(AccessController.getContext())); // > john
          });
      // end::RunContexts.empty[]
    }

    {
      // tag::RunContexts.copyCurrent[]
      RunContexts.copyCurrent()
          .withLocale(Locale.US)
          .run(() -> {
            // run some code
          });
      // end::RunContexts.copyCurrent[]
    }
  }
}
