package org.eclipse.scout.contacts.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.scout.contacts.server.organization.OrganizationService;
import org.eclipse.scout.contacts.server.sql.DBSetupService;
import org.eclipse.scout.contacts.server.sql.DerbySqlService;
import org.eclipse.scout.contacts.shared.organization.OrganizationFormData;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for
 * <h3>{@link OrganizationService}</h3>
 */
@RunWith(ServerTestRunner.class)
@RunWithServerSession(ServerSession.class)
@RunWithSubject("default")
public class OrganizationServiceTest {

  private OrganizationFormData m_testOrg = new OrganizationFormData();

  @BeforeClass
  public static void setupDatabase() {
    BEANS.get(DBSetupService.class).createOrganizationTable();
  }

  @AfterClass
  public static void destroyDBConnections() {
    BEANS.get(DerbySqlService.class).dropDB();
    BEANS.get(DerbySqlService.class).destroySqlConnectionPool();
  }

  @Before
  public void before() {
    m_testOrg.getName().setValue("test");
    m_testOrg.getEmail().setValue("testEmail");
    m_testOrg.getPhone().setValue("000");
  }

  @Test
  public void testCreate() {
    OrganizationService svc = new OrganizationService();
    OrganizationFormData created = svc.create(m_testOrg);
    assertTestOrg(created);
    assertNotNull(created.getOrganizationId());
  }

  @Test
  public void testLoad() {
    OrganizationService svc = new OrganizationService();
    m_testOrg.setOrganizationId("testId");
    svc.create(m_testOrg);
    OrganizationFormData res = svc.load(m_testOrg);
    assertTestOrg(res);
    assertEquals("testId", res.getOrganizationId());
  }

  @Test
  public void testStore() {
    OrganizationService svc = new OrganizationService();
    m_testOrg.setOrganizationId("testId2");
    OrganizationFormData created = svc.create(m_testOrg);
    created.getName().setValue("newName");
    OrganizationFormData res = svc.store(m_testOrg);
    assertEquals("testId2", res.getOrganizationId());
    assertEquals("newName", res.getName().getValue());
  }

  private void assertTestOrg(OrganizationFormData testOrg) {
    assertNotNull(testOrg);
    assertEquals("test", testOrg.getName().getValue());
    assertEquals("testEmail", testOrg.getEmail().getValue());
    assertEquals("000", testOrg.getPhone().getValue());
  }

}
