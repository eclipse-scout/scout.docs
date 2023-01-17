/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.widgets.shared.services.code;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;
import org.eclipse.scout.rt.shared.services.common.code.CodeRow;
import org.eclipse.scout.rt.shared.services.common.code.ICodeRow;

/**
 * @author mzi
 */
@ClassId("8e558241-1d69-4a17-8f3a-afe9f6a081b3")
public class ColorsCodeType extends AbstractCodeType<Long, Color> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 20000L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Colors");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Override
  protected List<? extends ICodeRow<Color>> execLoadCodes(Class<? extends ICodeRow<Color>> codeRowType) {
    List<ICodeRow<Color>> codes = new ArrayList<>();

    codes.add(new CodeRow<>(Color.PINK, TEXTS.get("Pink")));
    codes.add(new CodeRow<>(Color.RED, TEXTS.get("Red")));
    codes.add(new CodeRow<>(Color.WHITE, TEXTS.get("White")));
    codes.add(new CodeRow<>(Color.YELLOW, TEXTS.get("YellowDynamic")));

    return codes;
  }

  @Order(10)
  @ClassId("815af799-0eb0-454d-b6d3-ce67289cc05b")
  public static class BlackCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.BLACK;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Black");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(80)
  @ClassId("dd2d969e-46fd-48a6-9d5e-04c0c51ccca3")
  public static class BlueCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.BLUE;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Blue");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(90)
  @ClassId("fb0c1bec-149a-466b-8832-e380b363a317")
  public static class CyanCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.CYAN;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Cyan");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("85c6afa7-e24c-46d3-8117-19fd845b5918")
  public static class DarkGrayCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.DARK_GRAY;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("DarkGray");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(30)
  @ClassId("229ce162-a3e6-47d8-ae93-2b4a9ab84911")
  public static class GrayCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.GRAY;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Gray");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(70)
  @ClassId("0a273c74-d668-4f7a-9c59-45355ae8f63f")
  public static class GreenCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.GREEN;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Green");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(40)
  @ClassId("8cd48f6d-a2b0-49c5-a8db-97bd5dce7315")
  public static class LightGrayCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.LIGHT_GRAY;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("LightGray");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(100)
  @ClassId("66e8065b-c5be-48d8-baea-b6a7c6fdf9e1")
  public static class MagentaCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.MAGENTA;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Magenta");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(110)
  @ClassId("71b1ab86-3694-4914-8055-687cd0b4fb8c")
  public static class OrangeCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.ORANGE;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Orange");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }

  @Order(120)
  @ClassId("049188e9-b0e7-4b8d-84f6-50483fa5aa82")
  public static class YellowCode extends AbstractCode<Color> {
    private static final long serialVersionUID = 1L;
    public static final Color ID = Color.YELLOW;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Yellow");
    }

    @Override
    public Color getId() {
      return ID;
    }
  }
}
