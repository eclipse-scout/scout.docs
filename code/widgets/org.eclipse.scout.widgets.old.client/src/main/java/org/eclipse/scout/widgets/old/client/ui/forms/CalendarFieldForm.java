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
package org.eclipse.scout.widgets.old.client.ui.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.CalendarMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.root.ContextMenuEvent;
import org.eclipse.scout.rt.client.ui.action.menu.root.ContextMenuListener;
import org.eclipse.scout.rt.client.ui.action.menu.root.IContextMenu;
import org.eclipse.scout.rt.client.ui.basic.calendar.AbstractCalendar;
import org.eclipse.scout.rt.client.ui.basic.calendar.provider.AbstractCalendarItemProvider;
import org.eclipse.scout.rt.client.ui.desktop.outline.OutlineMenuWrapper;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.calendarfield.AbstractCalendarField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.SleepUtil;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.calendar.CalendarAppointment;
import org.eclipse.scout.rt.shared.services.common.calendar.ICalendarItem;
import org.eclipse.scout.widgets.client.ui.forms.IPageForm;
import org.eclipse.scout.widgets.old.client.ui.forms.CalendarFieldForm.MainBox.CalendarField;
import org.eclipse.scout.widgets.old.client.ui.forms.CalendarFieldForm.MainBox.CloseButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalendarFieldForm extends AbstractForm implements IPageForm {

  private static final Logger LOG = LoggerFactory.getLogger(CalendarFieldForm.class);

  public CalendarFieldForm() {
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
  public class MainBox extends AbstractGroupBox {

    @Order(10)
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

      @Override
      protected boolean getConfiguredStatusVisible() {
        return false;
      }

      @Order(10)
      public class Calendar extends AbstractCalendar {

        private ContextMenuListener m_cml;

        private List<IMenu> m_calendarMenus = new ArrayList<>();

        @Override
        protected void execInitCalendar() {
          m_cml = new ContextMenuListener() {
            @Override
            public void contextMenuChanged(ContextMenuEvent event) {
              if (ContextMenuEvent.TYPE_STRUCTURE_CHANGED == event.getType()) {
                IContextMenu rootContextMenu = getForm().getRootGroupBox().getContextMenu();
                rootContextMenu.removeChildActions(m_calendarMenus);
                m_calendarMenus.clear();
                for (IMenu menu : event.getSource().getChildActions()) {
                  m_calendarMenus.add(OutlineMenuWrapper.wrapMenu(menu));
                }
                rootContextMenu.addChildActions(m_calendarMenus);
              }
            }
          };
          getCalendar().getContextMenu().addContextMenuListener(m_cml);
        }

        @Override
        protected void execDisposeCalendar() {
          getCalendar().getContextMenu().removeContextMenuListener(m_cml);
        }

        @Order(10)
        public class ItemProvdider01 extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItemsInBackground(IClientSession session, Date minDate, Date maxDate, Set<ICalendarItem> result) {
            LOG.info("ItemProvdider01#execLoadItemsInBackground");
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

          @Order(210)
          public class Provider1EmptySpaceMenu extends AbstractMenu {

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

        @Order(20)
        public class ItemProvdider02 extends AbstractCalendarItemProvider {

          @Override
          protected void execLoadItems(Date minDate, Date maxDate, final Set<ICalendarItem> result) {
            LOG.info("ItemProvdider02#execLoadItems");

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
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
