/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.ui.CssClasses;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.platform.util.TriState;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CalendarField;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CalendarField.Calendar;
import org.eclipse.scout.widgets.client.ui.forms.CalendarFieldForm.MainBox.CloseButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(8200)
@ClassId("0d17b2a7-99c6-4de4-b1fa-8e29a9482480")
public class CalendarFieldForm extends AbstractForm implements IAdvancedExampleForm {

  private static final Logger LOG = LoggerFactory.getLogger(CalendarFieldForm.class);

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("CalendarField");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public CalendarField getCalendarField() {
    return getFieldByClass(CalendarField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10)
  @ClassId("3c2d9d0c-047c-4cf9-9ee9-a3f751990687")
  public class MainBox extends AbstractGroupBox {

    @Override
    protected TriState getConfiguredScrollable() {
      return TriState.FALSE;
    }

    @Override
    protected String getConfiguredCssClass() {
      return CssClasses.RIGHT_PADDING_INVISIBLE;
    }

    @Order(10)
    @ClassId("63311e33-b5d9-422c-915f-9aa7cd44a4f0")
    public class CalendarField extends AbstractCalendarField<Calendar> {

      @Override
      protected int getConfiguredGridW() {
        return 0;
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      @Override
      protected boolean getConfiguredStatusVisible() {
        return false;
      }

      @Order(10)
      @ClassId("cec3a7ca-ea72-4402-8676-9cde7a119312")
      public class Calendar extends AbstractCalendar {

        @Override
        protected void execInitCalendar() {
          setMenuInjectionTarget(getMainBox());
        }

        @Order(10)
        public class ItemProvider01 extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItemsInBackground(IClientSession session, Date minDate, Date maxDate, Set<ICalendarItem> result) {
            LOG.info("ItemProvider01#execLoadItemsInBackground");
            System.out.println("START long running item fetch operation... ");
            SleepUtil.sleepSafe(5, TimeUnit.SECONDS);
            System.out.println("END item fetch operation");

            String cssClass = "calendar-appointment";
            java.util.Calendar cal = java.util.Calendar.getInstance();
            Date start = cal.getTime();
            Date end = cal.getTime();
            result.add(new CalendarAppointment(0L, 0L, start, end, true, "FULL DAY [P1]", "This appointment takes the full day", cssClass));
            cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
            start = cal.getTime();
            cal.add(java.util.Calendar.HOUR, 2);
            end = cal.getTime();
            result.add(new CalendarAppointment(1L, 2L, start, end, false, "app1 [P1]", "appointment1 body", cssClass));

            cal.add(java.util.Calendar.HOUR, 1);
            start = cal.getTime();
            cal.add(java.util.Calendar.MINUTE, 30);
            end = cal.getTime();
            result.add(new CalendarAppointment(2L, 2L, start, end, false, "app2 [P1]", "appointment2 body", cssClass));

            // future
            cal.setTime(new Date());
            cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
            start = cal.getTime();
            cal.add(java.util.Calendar.HOUR, 48);
            end = cal.getTime();
            result.add(new CalendarAppointment(3L, 2L, start, end, false, "app3 [P1]", null, cssClass));
            cal.add(java.util.Calendar.HOUR, 2);
            end = cal.getTime();

            result.add(new CalendarAppointment(4L, 2L, start, end, false, null, "appointment4 body", cssClass));
          }

          @Order(200)
          @ClassId("0ad6cbc8-eca2-4861-8de2-fe037a27b838")
          public class Provider1ComponentMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
            }
          }

          @Order(10)
          @ClassId("9388992e-9948-4b21-8196-884c2f7674a1")
          public class Provider1ComponentLevel1FlatSubMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
            }
          }

          @Order(210)
          @ClassId("8b151712-0f7e-440d-b9f7-d542959865de")
          public class Provider1ComponentLevel1TreeSubMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
            }

            @Order(10)
            @ClassId("5eef856a-f0cb-47f1-bc2c-ace3551328d1")
            public class Provider1ComponentLevel2SubMenu extends AbstractMenu {

              @Override
              protected String getConfiguredText() {
                return getClass().getSimpleName();
              }

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
              }

              @Order(10)
              @ClassId("9388992e-9948-4b21-8196-884c2f7674a1")
              public class Provider1ComponentLevel3SubMenu extends AbstractMenu {

                @Override
                protected String getConfiguredText() {
                  return getClass().getSimpleName();
                }

                @Override
                protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                  return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
                }
              }
            }
          }
        }

        @Order(20)
        public class ItemProvider02 extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItems(Date minDate, Date maxDate, final Set<ICalendarItem> result) {
            LOG.info("ItemProvider02#execLoadItems");

            String cssClass = "calendar-task";
            java.util.Calendar cal = java.util.Calendar.getInstance();
            Date start = cal.getTime();
            Date end = cal.getTime();
            result.add(new CalendarAppointment(5L, 0L, start, end, true, "FULL DAY [P2]", "This appointment takes the full day", cssClass));
            cal.add(java.util.Calendar.DAY_OF_YEAR, -1);
            start = cal.getTime();
            cal.add(java.util.Calendar.HOUR, 2);
            end = cal.getTime();
            result.add(new CalendarAppointment(6L, 2L, start, end, false, "app1 [P2]", "appointment1 body", cssClass));

            cal.add(java.util.Calendar.HOUR, 1);
            start = cal.getTime();
            cal.add(java.util.Calendar.MINUTE, 30);
            end = cal.getTime();
            result.add(new CalendarAppointment(7L, 2L, start, end, false, "app2 [P2]", "appointment2 body", cssClass));

            cal.add(java.util.Calendar.HOUR, 1);
            start = cal.getTime();
            cal.add(java.util.Calendar.MINUTE, 30);
            end = cal.getTime();
            result.add(new CalendarAppointment(8L, 2L, start, end, false, "app2 [P2]", "appointment2 body", null));

            // future
            cal.setTime(new Date());
            cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
            start = cal.getTime();
            cal.add(java.util.Calendar.HOUR, 48);
            end = cal.getTime();
            result.add(new CalendarAppointment(8L, 2L, start, end, false, "app3 [P2]", "appointment3 body", cssClass));
            cal.add(java.util.Calendar.HOUR, 2);
            end = cal.getTime();
            result.add(new CalendarAppointment(9L, 2L, start, end, false, "app4 [P2]", "appointment4 body", cssClass));

            // multi-day
            cal.setTime(new Date());
            cal.add(java.util.Calendar.DAY_OF_YEAR, 4);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 17); // set to 17:00
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.set(java.util.Calendar.SECOND, 0);
            start = cal.getTime();
            cal.add(java.util.Calendar.HOUR, 15); // add 15 hours -> 08:00 the next day
            end = cal.getTime();
            result.add(new CalendarAppointment(10L, 2L, start, end, false, "app5 [P2]", "appointment5 body", cssClass));
          }

          @Order(200)
          @ClassId("d764e7e9-7f30-4481-9736-29697df339e3")
          public class Provider2ComponentMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.CalendarComponent);
            }
          }

          @Order(210)
          @ClassId("324606df-cef5-4a06-b1c2-f87eedc32963")
          public class Provider2EmptySpaceMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
            }
          }
        }

        @Order(200)
        @ClassId("dce702fa-492e-4d6b-aba7-7a409fef4261")
        public class CalendarEmptySpaceMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return getClass().getSimpleName();
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(CalendarMenuType.EmptySpace);
          }
        }
      }
    }

    @Order(30)
    @ClassId("bf306b56-a4ab-4173-8b28-8d877d695bb4")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
