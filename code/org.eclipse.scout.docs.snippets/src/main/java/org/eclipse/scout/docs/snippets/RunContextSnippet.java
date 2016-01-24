package org.eclipse.scout.docs.snippets;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.nls.NlsLocale;
import org.eclipse.scout.rt.platform.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;

public final class RunContextSnippet {

  void snippets() throws Exception {
    {
      // tag::RunContexts.empty[]
      Subject subject = new Subject(); // <1>
      subject.getPrincipals().add(new SimplePrincipal("john"));
      subject.setReadOnly();

      RunContexts.empty()
          .withSubject(subject)
          .withLocale(Locale.US)
          .run(new IRunnable() { // <2>

            @Override
            public void run() throws Exception {
              // run some code <3>
              System.out.println(NlsLocale.CURRENT.get()); // > Locale.US
              System.out.println(Subject.getSubject(AccessController.getContext())); // > john
            }
          });
      // end::RunContexts.empty[]
    }

    {
      // tag::RunContexts.copyCurrent[]
      RunContexts.copyCurrent()
          .withLocale(Locale.US)
          .run(new IRunnable() {

            @Override
            public void run() throws Exception {
              // run some code
            }
          });
      // end::RunContexts.copyCurrent[]
    }
  }
}
