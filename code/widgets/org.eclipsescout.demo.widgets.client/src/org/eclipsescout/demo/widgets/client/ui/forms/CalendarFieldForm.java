/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.DateUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.CalendarComponent;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.ICalendarItemProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.ExamplesBox.CalendarField;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.ExamplesBox.CalendarField.Calendar.AppointmentProvider;
import org.eclipsescout.demo.widgets.client.ui.forms.CalendarFieldForm.MainBox.ExamplesBox.CalendarField.Calendar.BirthdayProvider;

public class CalendarFieldForm extends AbstractForm implements IPageForm {

  public CalendarFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CalendarField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  public CalendarField getCalendarField() {
    return getFieldByClass(CalendarField.class);
  }

  public ExamplesBox getGroupBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class CalendarField extends AbstractCalendarField<CalendarField.Calendar> {

        @Override
        protected int getConfiguredGridH() {
          return 20;
        }

        @Override
        protected int getConfiguredGridW() {
          return 0;
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class Calendar extends AbstractCalendar {

          @Override
          protected void execInitCalendar() throws ProcessingException {
            setDisplayMode(DISPLAY_MODE_MONTH);
          }

          @Order(10)
          public class AppointmentProvider extends AbstractCalendarItemProvider {

            public static final String COLOR = "5B95C2";
            public static final String COLOR_USER = "91BBDD";

            private CalendarItemStore m_store;

            public AppointmentProvider() {
              super();
              m_store = new CalendarItemStore();
            }

            public void initCalendarItems() {
              m_store.addAppointment(createDate(8, 0, 0), createDate(9, 0, 0), true, "OUT OF OFFICE", "Hiking in the mountains", COLOR);
              m_store.addAppointment(createDate(9, 0, -1), createDate(10, 30, -1), false, "Meet Bob", "Discuss project goals", COLOR);

              m_store.addAppointment(createDate(10, 0, 0), createDate(10, 15, 0), false, "Daily Standup", "Project ACME standup meeting", COLOR);
              m_store.addAppointment(createDate(10, 0, 1), createDate(10, 15, 1), false, "Daily Standup", "Project ACME standup meeting", COLOR);
              m_store.addAppointment(createDate(10, 0, 2), createDate(10, 15, 2), false, "Daily Standup", "Project ACME standup meeting", COLOR);

              m_store.addAppointment(createDate(19, 30, 2), createDate(10, 0, 4), false, "Trip Abroad", "Flying to favorite place and stay overnight", COLOR);
            }

            @Override
            protected void execLoadItems(Date minDate, Date maxDate, final Set<ICalendarItem> result) throws ProcessingException {
              result.clear();
              result.addAll(m_store.getItems());
            }

            @Order(10)
            public class OpenMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("OpenAppointment_");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
              }

              @Override
              protected void execAction() throws ProcessingException {
                CalendarComponent comp = getSelectedComponent();
                ICalendarItem item = getSelectedComponent().getItem();
                MessageBox.showOkMessage("Appointment " + comp.getFromDate(), item.getSubject(), item.getBody());
              }
            }

            @Order(10)
            public class RemoveMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("RemoveAppointment");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
              }

              @Override
              protected void execAction() throws ProcessingException {
                String itemId = (String) getSelectedComponent().getItem().getItemId();

                m_store.removeItem(itemId);
                reloadProvider();
              }
            }

            @Order(20)
            public class AddMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("AddAppointment_");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
              }

              @Override
              protected void execAction() throws ProcessingException {
                Date start = DateUtility.addHours(getSelectedDate(), 9 + (new Random()).nextInt(9));
                Date end = DateUtility.addMinutes(start, 10 * ((new Random()).nextInt(8) + 1));

                m_store.addAppointment(start, end, false, "Break", "Take a break", COLOR_USER);
                reloadProvider();
              }
            }

          }

          @Order(20)
          public class BirthdayProvider extends AbstractCalendarItemProvider {

            public static final String COLOR = "FAA635";
            public static final String COLOR_USER = "FADD35";
            private CalendarItemStore m_store;

            public BirthdayProvider() {
              super();
              m_store = new CalendarItemStore();
            }

            public void initCalendarItems() {
              m_store.addBirthday(createDate(8, 0, 0), "Birthday Alice", "Send her flowers", COLOR);
              m_store.addBirthday(createDate(8, 0, 2), "Birthday Bob", "Send him a cake", COLOR);
            }

            @Override
            protected void execLoadItems(Date minDate, Date maxDate, final Set<ICalendarItem> result) throws ProcessingException {
              result.clear();
              result.addAll(m_store.getItems());
            }

            @Order(100)
            public class RemoveMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("RemoveBirthday");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
              }

              @Override
              protected void execAction() throws ProcessingException {
                String itemId = (String) getSelectedComponent().getItem().getItemId();

                m_store.removeItem(itemId);
                reloadProvider();
              }
            }

            @Order(300)
            public class AddMenu extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("AddBirthday_");
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
              }

              @Override
              protected void execAction() throws ProcessingException {
                Date date = getSelectedDate();
                char c = (char) ('A' + (new Random()).nextInt(26));
                String subject = "Birthday " + c + ".";

                m_store.addBirthday(date, subject, "Send a card", COLOR_USER);
                reloadProvider();
              }
            }
          }

          private Date createDate(int hour, int minute, int deltaDay) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.add(java.util.Calendar.DAY_OF_YEAR, deltaDay);
            cal.set(java.util.Calendar.HOUR_OF_DAY, hour);
            cal.set(java.util.Calendar.MINUTE, minute);

            return cal.getTime();
          }

          private class CalendarItemStore {
            private Hashtable<String, ICalendarItem> m_store;

            public CalendarItemStore() {
              m_store = new Hashtable<String, ICalendarItem>();
            }

            public void addAppointment(Date start, Date end, boolean fullDay, String subject, String body, String color) {
              String id = createId();
              Long person = 0L;
              CalendarAppointment item = new CalendarAppointment(id, person, start, end, fullDay, subject, body, color);
              m_store.put(id, item);
            }

            public void addBirthday(Date date, String subject, String body, String color) {
              addAppointment(date, date, true, subject, body, color);
            }

            public void removeItem(String id) {
              m_store.remove(id);
            }

            public Set<ICalendarItem> getItems() {
              return new HashSet<ICalendarItem>(m_store.values());
            }

            private String createId() {
              return UUID.randomUUID().toString();
            }
          }
        }
      }
    }

    @Order(30.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        for (ICalendarItemProvider provider : getCalendarField().getCalendar().getCalendarItemProviders()) {
          if (provider instanceof AppointmentProvider) {
            ((AppointmentProvider) provider).initCalendarItems();
          }
          else if (provider instanceof BirthdayProvider) {
            ((BirthdayProvider) provider).initCalendarItems();
          }
        }

        getCalendarField().reloadCalendarItems();
      }
    }

    @Order(40.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
