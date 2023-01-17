/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
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
