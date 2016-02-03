package org.eclipse.scout.widgets.shared.services.code;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class SeverityCodeType extends AbstractCodeType<Long, Integer> {

  private static final long serialVersionUID = 1L;
  public static final Long ID = 10001L;

  public SeverityCodeType() {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("SeverityType");
  }

  @Override
  public Long getId() {
    return ID;
  }

  @Order(10)
  public static class OkCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.OK;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Ok");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(20)
  public static class InfoCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.INFO;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Info");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(30)
  public static class WarningCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.WARNING;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Warning");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }

  @Order(40)
  public static class ErrorCode extends AbstractCode<Integer> {

    private static final long serialVersionUID = 1L;
    public static final int ID = IStatus.ERROR;

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Error");
    }

    @Override
    public Integer getId() {
      return ID;
    }
  }
}
