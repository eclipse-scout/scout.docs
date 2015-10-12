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
package org.eclipse.scout.docs.publish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtility {

  public static void zipFolder(File srcFolder, File destZipFile) {
    ZipOutputStream zip = null;
    FileOutputStream fileWriter = null;

    try {
      fileWriter = new FileOutputStream(destZipFile);
      zip = new ZipOutputStream(fileWriter);

      addFolderToZip("", srcFolder.getCanonicalPath(), zip);
      zip.flush();
      zip.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) {

    File folder = new File(srcFile);
    if (folder.isDirectory()) {
      addFolderToZip(path, srcFile, zip);
    }
    else {
      byte[] buf = new byte[1024];
      int len;
      try (FileInputStream in = new FileInputStream(srcFile)) {
        zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
        while ((len = in.read(buf)) > 0) {
          zip.write(buf, 0, len);
        }
        in.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) {
    File folder = new File(srcFolder);

    for (String fileName : folder.list()) {
      if (path.equals("")) {
        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
      }
      else {
        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
      }
    }
  }
}
