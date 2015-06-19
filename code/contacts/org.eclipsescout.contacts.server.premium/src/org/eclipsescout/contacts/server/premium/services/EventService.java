/**
 *
 */
package org.eclipsescout.contacts.server.premium.services;

import java.util.UUID;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.ITableHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.TableBeanHolderFilter;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipsescout.contacts.shared.premium.CreateEventPermission;
import org.eclipsescout.contacts.shared.premium.EventFormData;
import org.eclipsescout.contacts.shared.premium.IEventService;
import org.eclipsescout.contacts.shared.premium.ReadEventPermission;
import org.eclipsescout.contacts.shared.premium.UpdateEventPermission;

/**
 * @author mzi
 */
public class EventService extends AbstractService implements IEventService {

  @Override
  public EventFormData create(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (StringUtility.isNullOrEmpty(formData.getEventId())) {
      formData.setEventId(UUID.randomUUID().toString());
    }

    SQL.insert(TEXTS.get("SqlEventInsert"), formData);

    return store(formData);
  }

  @Override
  public EventFormData load(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadEventPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.selectInto(TEXTS.get("SqlEventSelect"), formData);
    SQL.selectInto(TEXTS.get("SqlEventParticipantsSelect"), formData);

    return formData;
  }

  @Override
  public EventFormData prepareCreate(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateEventPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    return formData;
  }

  @Override
  public EventFormData store(EventFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateEventPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.update(TEXTS.get("SqlEventUpdate"), formData);
    updateParticipants(formData);

    return formData;
  }

  private void updateParticipants(EventFormData formData) throws ProcessingException {
    TableBeanHolderFilter deletedParticipants = new TableBeanHolderFilter(formData.getParticipants(), ITableHolder.STATUS_DELETED);
    TableBeanHolderFilter insertedParticipants = new TableBeanHolderFilter(formData.getParticipants(), ITableHolder.STATUS_INSERTED);
    NVPair eventId = new NVPair("eventId", formData.getEventId());

    SQL.delete(TEXTS.get("SqlEventParticipantsDelete"), deletedParticipants, eventId);
    SQL.insert(TEXTS.get("SqlEventParticipantsInsert"), insertedParticipants, eventId);
  }
}
