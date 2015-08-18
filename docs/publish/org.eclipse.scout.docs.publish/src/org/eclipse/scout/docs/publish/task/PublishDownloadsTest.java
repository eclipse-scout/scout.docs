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
import org.eclipse.scout.docs.publish.ZipUtility;
import org.junit.Test;

/**
 * Task as JUnit Test to prepare publications of downloads (book).
 * Output folder: target/published-docs/downloads
 * Corresponding Git Repository: http://git.eclipse.org/c/scout/org.eclipse.scout.sdk.git/
 */
public class PublishDownloadsTest {

  private File outRootFolder = new File("target/published-docs/downloads");

  private File outHelloWorld = new File(outRootFolder, "article_helloworld");
  private File outInstall = new File(outRootFolder, "article_install");
  private File outBookIntro = new File(outRootFolder, "book_scout_intro");
  private File outBookFrontend = new File(outRootFolder, "book_scout_frontend");
  private File outBookArchitecture = new File(outRootFolder, "book_scout_architecture");

  @Test
  public void publishHelloWorldAsHtmlAndZip() throws IOException {
    File inFolder = new File("../../build/scout_helloworld/target/generated-docs");
    publishAsHtmlAndZip(inFolder, outHelloWorld, "scout_helloworld");
  }

  @Test
  public void publishHelloWorldAsPdf() throws IOException {
    File inFolder = new File("../../build/scout_helloworld/target/generated-docs");
    String inFileName = "scout_helloworld.pdf";

    PublishUtility.publishPdfFile(inFolder, inFileName, outHelloWorld);
  }

  @Test
  public void publishScoutInstallAsHtmlAndZip() throws IOException {
    File inFolder = new File("../../build/scout_install/target/generated-docs");
    publishAsHtmlAndZip(inFolder, outInstall, "scout_install");
  }

  @Test
  public void publishScoutInstallAsPdf() throws IOException {
    File inFolder = new File("../../build/scout_install/target/generated-docs");
    String inFileName = "scout_install.pdf";

    PublishUtility.publishPdfFile(inFolder, inFileName, outInstall);
  }

  @Test
  public void publishBookIntroAsHtmlAndZip() throws IOException {
    File inFolder = new File("../../build/book_scout_intro/target/generated-docs");
    publishAsHtmlAndZip(inFolder, outBookIntro, "scout_intro");
  }

  @Test
  public void publishBookIntroAsPdf() throws IOException {
    File inFolder = new File("../../build/book_scout_intro/target/generated-docs");
    String inFileName = "scout_intro.pdf";

    PublishUtility.publishPdfFile(inFolder, inFileName, outBookIntro);
  }

  @Test
  public void publishBookFrontEndAsHtmlAndZip() throws IOException {
    File inFolder = new File("../../build/book_scout_frontend/target/generated-docs");
    publishAsHtmlAndZip(inFolder, outBookFrontend, "scout_frontend");
  }

  @Test
  public void publishBookFrontEndAsPdf() throws IOException {
    File inFolder = new File("../../build/book_scout_frontend/target/generated-docs");
    String inFileName = "scout_frontend.pdf";

    PublishUtility.publishPdfFile(inFolder, inFileName, outBookFrontend);
  }

  @Test
  public void publishBookArchitectureAsHtmlAndZip() throws IOException {
    File inFolder = new File("../../build/book_scout_architecture/target/generated-docs");
    publishAsHtmlAndZip(inFolder, outBookArchitecture, "scout_architecture");
  }

  @Test
  public void publishBookArchitectureAsPdf() throws IOException {
    File inFolder = new File("../../build/book_scout_architecture/target/generated-docs");
    String inFileName = "scout_architecture.pdf";

    PublishUtility.publishPdfFile(inFolder, inFileName, outBookArchitecture);
  }

  private void publishAsHtmlAndZip(File inFolder, File outFolder, String name) throws IOException {
    String inFileName = name + ".html";
    File outHtmlFolder = new File(outFolder, name);

    PublishUtility.publishHtmlFile(inFolder, inFileName, outHtmlFolder);

    File outZipFile = new File(outFolder, name + ".zip");
    ZipUtility.zipFolder(outHtmlFolder, outZipFile);
  }
}
