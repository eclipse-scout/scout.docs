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
package org.eclipse.scout.docs.publish.task;

import java.io.File;
import java.io.IOException;

import org.eclipse.scout.docs.publish.PublishUtility;
import org.junit.Test;

/**
 * Task as JUnit Test to prepare publication to the eclipse.org/scout site.
 * Output folder: target/published-docs/eclipse.org-scout
 * Corresponding Git Repository: http://git.eclipse.org/c/www.eclipse.org/scout.git
 */
public class PublishToWwwScoutTest {

  private File outFolder = new File("target/published-docs/eclipse.org-scout");

  @Test
  public void publishHelloWorld() throws IOException {
    File inFolder = new File("../../build/scout_helloworld/target/generated-docs");
    String inFileName = "scout_helloworld.html";

    PublishUtility.publishHtmlFile(inFolder, inFileName, outFolder);
  }

  @Test
  public void publishScoutInstall() throws IOException {
    File inFolder = new File("../../build/scout_install/target/generated-docs");
    String inFileName = "scout_install.html";

    PublishUtility.publishHtmlFile(inFolder, inFileName, outFolder);
  }

}
