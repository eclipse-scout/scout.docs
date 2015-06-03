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

import org.eclipse.scout.docs.publish.PublishHelpUtility;
import org.junit.Test;

/**
 * Task as JUnit Test to prepare publication to the org.eclipse.scout.sdk.help plugin.
 * Output folder: target/published-docs/scout.sdk.help
 * Corresponding Git Repository: http://git.eclipse.org/c/scout/org.eclipse.scout.sdk.git/
 */
public class PublishToEclipseHelpTest {

  private File outFolder = new File("target/published-docs/scout-sdk-help");

  @Test
  public void publishEclipseHelp() throws IOException {
    File inFolder = new File("../../build/eclipse_help/target/generated-docs");
    File listOfPagesFile = new File("../../build/eclipse_help/help-pages.txt");

    PublishHelpUtility.publishEclipseHelp(inFolder, outFolder, listOfPagesFile);
  }
}
