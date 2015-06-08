package org.eclipse.scout.docs.snippets;

import java.security.AccessController;
import java.util.Locale;

import javax.security.auth.Subject;

import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.nls.NlsLocale;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.context.RunContexts;

public final class RunContextSnippet {

  void snippet_createEmptyRunContext() throws Exception {
    // tag::RunContexts.empty[]
    Subject subject = new Subject(); // <1>
    subject.getPrincipals().add(new SimplePrincipal("john"));
    subject.setReadOnly();

    RunContexts.empty().subject(subject).locale(Locale.US).run(new IRunnable() { // <2>

      @Override
      public void run() throws Exception {
        // run some code <3>
        System.out.println(NlsLocale.CURRENT.get()); // > Locale.US
        System.out.println(Subject.getSubject(AccessController.getContext())); // > john
      }
    });
    // end::RunContexts.empty[]
  }

  void snippet_copyCurrentRunContext() throws Exception {
    // tag::RunContexts.copyCurrent[]
    RunContexts.copyCurrent().locale(Locale.US).run(new IRunnable() {

      @Override
      public void run() throws Exception {
        // run some code
      }
    });
    // end::RunContexts.copyCurrent[]
  }
}
