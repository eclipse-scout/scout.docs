package org.eclipse.scout.docs.snippets.codetype;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

@ClassId("fb11128e-08e7-47f1-a6ae-b988373f81b5")
public class YesOrNoCodeType extends AbstractCodeType<Long, Boolean> {

  private static final long serialVersionUID = 1L;

  public static final Long ID = 10000L;

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("text");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  @ClassId("2f534418-989c-4a63-a1f1-69d8cae1127f")
  public static class YesCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10010L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Yes");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

  @Order(20)
  @ClassId("c47318ba-599b-4ab3-b0ea-97507a557655")
  public static class NoCode extends AbstractCode<Long> {

    private static final long serialVersionUID = 1L;
    public static final Long ID = 10020L;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("No");
    }

    @Override
    public Long getId() {
      return ID;
    }
  }

}
