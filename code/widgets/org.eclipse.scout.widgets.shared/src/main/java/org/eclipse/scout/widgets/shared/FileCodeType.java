/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.shared;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

/**
 * @author mzi
 */
@ClassId("783aa07b-ef09-439c-af2d-82e091294c83")
public class FileCodeType extends AbstractCodeType<String, String> {

  private static final long serialVersionUID = 1L;

  public static final String ID = "File Types";

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("File");
  }

  @Override
  public String getId() {
    return ID;
  }

  @Order(10)
  @ClassId("9254e7f8-f394-4a72-820e-8841d71f95bd")
  public static class HtmlCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "html";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("HTMLDocument");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("e006acd4-d9a2-4364-b4dd-b83a27320cd6")
  public static class TextCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "txt";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("TextDocument");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(30)
  @ClassId("c836bf15-d177-464d-aa23-dd08cb84a79a")
  public static class JpgCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "jpg";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("JpgImage");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(40)
  @ClassId("f3c006ca-8dd6-4b7b-a92e-04b285e3d50e")
  public static class PngCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "png";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("PngImage");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(50)
  @ClassId("a640329d-f5b8-4ad8-b5cf-6f76a5744be6")
  public static class JavaCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    public static final String ID = "java";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("JavaFile");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(60)
  @ClassId("01418936-e9e0-4f71-a74b-6d033cf534f1")
  public static class PdfCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;

    public static final String ID = "pdf";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("PdfDocument");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(70)
  @ClassId("baecf52d-c371-435b-8c41-2943eb7ee0bb")
  public static class UknownCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;

    public static final String ID = "__UNKNOWN__";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Unknown");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(80)
  @ClassId("9474f6f1-68dc-4f88-bde9-b2771c04dfd0")
  public static class DirectoryCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;

    public static final String ID = "__DIR__";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Directory");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}
