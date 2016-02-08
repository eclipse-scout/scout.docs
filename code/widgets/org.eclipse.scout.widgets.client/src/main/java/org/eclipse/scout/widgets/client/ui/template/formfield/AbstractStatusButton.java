package org.eclipse.scout.widgets.client.ui.template.formfield;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.status.Status;
import org.eclipse.scout.rt.shared.TEXTS;

@ClassId("e72c2a85-fb2c-453e-98e4-f555248e6cf9")
public abstract class AbstractStatusButton extends AbstractButton {

  @Override
  protected String getConfiguredLabel() {
    return TEXTS.get("Status");
  }

  @Override
  protected int getConfiguredDisplayStyle() {
    return DISPLAY_STYLE_LINK;
  }

  protected abstract IFormField getField();

  @Order(10)
  @ClassId("4b86815b-af73-406f-b1c7-f4b422fbe943")
  public class RemoveStatusMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return "(No status)";
    }

    @Override
    protected void execAction() {
      getField().clearErrorStatus();
    }
  }

  @Order(20)
  @ClassId("399d2e8c-5342-41b9-a25e-4d63fd35c5dd")
  public class SetSeverityInfoMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return "Severity INFO";
    }

    @Override
    protected void execAction() {
      getField().clearErrorStatus();
      getField().addErrorStatus(new Status("This is an information about this field.", IStatus.INFO));
    }
  }

  @Order(30)
  @ClassId("c6e83edf-4911-4a73-b474-c10a40c84c4f")
  public class SetSeverityWarningMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return "Severity WARNING";
    }

    @Override
    protected void execAction() {
      getField().clearErrorStatus();
      getField().addErrorStatus(new Status("This is a warning about this field.", IStatus.WARNING));
    }
  }

  @Order(40)
  @ClassId("4494bec2-b3cf-48b8-9974-dd93da05172b")
  public class SetSeverityErrorMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return "Severity ERROR";
    }

    @Override
    protected void execAction() {
      getField().clearErrorStatus();
      getField().addErrorStatus(new Status("An error has occurred on the field.", IStatus.ERROR));
    }
  }
}
