package org.eclipse.scout.contacts.events.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.contacts.events.client.person.PersonChooserForm;
import org.eclipse.scout.contacts.shared.person.IPersonLookupService;
import org.eclipse.scout.rt.client.testenvironment.TestEnvironmentClientSession;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.rt.testing.client.runner.ClientTestRunner;
import org.eclipse.scout.rt.testing.client.runner.RunWithClientSession;
import org.eclipse.scout.rt.testing.platform.mock.BeanMock;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for {@link PersonChooserForm}
 */
@RunWith(ClientTestRunner.class)
@RunWithClientSession(TestEnvironmentClientSession.class)
@RunWithSubject("default")
public class PersonChooserFormTest {

  @BeanMock
  private IPersonLookupService mock_service;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() {
    List<ILookupRow<String>> l = new ArrayList<>();
    ILookupRow<String> row = new LookupRow<String>("123", "Alice");
    l.add(row);
    when(mock_service.getDataByAll((ILookupCall<String>) any(ILookupCall.class))).thenReturn(l);
  }

  @Test
  public void testNoFilter() {
    PersonChooserForm f = new PersonChooserForm();
    List<? extends ILookupRow<String>> res = f.getPersonField().callBrowseLookup("", 100);
    assertEquals(1, res.size());
  }

  @Test
  public void testFilter() {
    PersonChooserForm f = new PersonChooserForm();
    List<String> persons = new ArrayList<>();
    persons.add("123");
    f.setFilteredPersons(persons);
    List<? extends ILookupRow<String>> res = f.getPersonField().callBrowseLookup("", 100);
    assertEquals(0, res.size());
  }

}
