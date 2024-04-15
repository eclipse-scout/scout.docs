/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets;

import java.util.UUID;

import org.eclipse.scout.rt.api.data.security.PermissionId;
import org.eclipse.scout.rt.dataobject.exception.AccessForbiddenException;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.security.ACCESS;
import org.eclipse.scout.rt.security.AbstractPermission;
import org.eclipse.scout.rt.security.IPermission;

public class AccessSnippet {


  //tag::ReadCompanyPermission[]
  public static class ReadCompanyPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("scoutdoc.ReadCompany");

    public ReadCompanyPermission() {
      super(ID);
    }
  }
  //end::ReadCompanyPermission[]

  //tag::CreateCompanyPermission[]
  public static class CreateCompanyPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("scoutdoc.CreateCompany");

    public CreateCompanyPermission() {
      super(ID);
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
  public static class UpdateCompanyPermission extends AbstractPermission {
    private static final long serialVersionUID = 1L;
    public static final PermissionId ID = PermissionId.of("scoutdoc.UpdateCompany");

    private final UUID m_companyId;

    public UpdateCompanyPermission() {
      this(null);
    }

    public UpdateCompanyPermission(UUID companyId) {
      super(ID);
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
