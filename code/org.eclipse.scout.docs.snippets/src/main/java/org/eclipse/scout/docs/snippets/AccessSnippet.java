package org.eclipse.scout.docs.snippets;

import java.util.UUID;

import org.eclipse.scout.rt.dataobject.exception.AccessForbiddenException;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.security.AbstractPermission;
import org.eclipse.scout.rt.security.IPermission;

public class AccessSnippet {

  //tag::ReadCompanyPermission[]
  public class ReadCompanyPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public ReadCompanyPermission() {
      super("scoutdoc.ReadCompany");
    }
  }
  //end::ReadCompanyPermission[]

  //tag::CreateCompanyPermission[]
  public class CreateCompanyPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    public CreateCompanyPermission() {
      super("scoutdoc.CreateCompany");
    }

    @Override
    public String getAccessCheckFailedMessage() {
      return TEXTS.get("YouAreNotAllowedToRegisterThisData");
    }
  }
  //end::CreateCompanyPermission[]

  protected void snippets() {

    //tag::ACCESS_A[]
    if (ACCESS.check(new ReadCompanyPermission())) { // <1>
      throw new AccessForbiddenException(TEXTS.get("YouAreNotAllowedToReadThisData"));
    }

    ACCESS.checkAndThrow(new ReadCompanyPermission()); // <2>
    //end::ACCESS_A[]

    //tag::ACCESS_B[]
    ACCESS.checkAllAndThrow(new ReadCompanyPermission(), new CreateCompanyPermission());

    ACCESS.checkAnyAndThrow(new ReadCompanyPermission(), new CreateCompanyPermission());
    //end::ACCESS_B[]

    UUID companyId = UUID.randomUUID();
    //tag::ACCESS_C[]
    ACCESS.checkAndThrow(new UpdateCompanyPermission(companyId));
    //end::ACCESS_C[]
  }

  //tag::UpdateCompanyPermission[]
  public class UpdateCompanyPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;

    private final UUID m_companyId;

    public UpdateCompanyPermission() {
      this(null);
    }

    public UpdateCompanyPermission(UUID companyId) {
      super("scoutdoc.UpdateCompany");
      m_companyId = companyId;
    }

    public UUID getCompanyId() {
      return m_companyId;
    }

    @Override
    protected boolean evalPermission(IPermission p) {
      // Precondition: p.getClass() == getClass() && getName().equals(p.getName()) &&
      //               getLevel() != PermissionLevel.NONE
      if (ScoutdocPermissionLevels.OWN == getLevel()) {
        UUID companyId = ((UpdateCompanyPermission) p).getCompanyId();
        return BEANS.get(ICompanyService.class).isOwnCompany(companyId);
      }
      return true; // ScoutdocPermissionLevels.ALL == getLevel()
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();
      result = prime * result + ((m_companyId == null) ? 0 : m_companyId.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj)) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      UpdateCompanyPermission other = (UpdateCompanyPermission) obj;
      if (m_companyId == null) {
        if (other.m_companyId != null) {
          return false;
        }
      }
      else if (!m_companyId.equals(other.m_companyId)) {
        return false;
      }
      return true;
    }
  }
  //end::UpdateCompanyPermission[]

  protected interface ICompanyService {

    boolean isOwnCompany(UUID companyId);

  }
}
