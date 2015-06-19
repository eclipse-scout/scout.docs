/**
 * 
 */
package org.eclipsescout.contacts.shared.services.code;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

/**
 * @author mzi
 */
public class GenderCodeType extends AbstractCodeType<String, String> {

  private static final long serialVersionUID = 1L;
  /**
   * 
   */
  public static final String ID = "Gender";

  /**
   * @throws org.eclipse.scout.commons.exception.ProcessingException
   */
  public GenderCodeType() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("Gender");
  }

  @Override
  public String getId() {
    return ID;
  }

  @Order(1000.0)
  public static class MaleCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    public static final String ID = "M";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Male");
    }

    @Override
    public String getId() {
      return ID;
    }
  }

  @Order(2000.0)
  public static class FemaleCode extends AbstractCode<String> {

    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    public static final String ID = "F";

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("Female");
    }

    @Override
    public String getId() {
      return ID;
    }
  }
}