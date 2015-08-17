/*******************************************************************************
 * Copyright (c) 2013 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.activitymap.AbstractActivityMap;
import org.eclipse.scout.rt.client.ui.basic.activitymap.ActivityCell;
import org.eclipse.scout.rt.client.ui.basic.activitymap.IActivityMap;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.plannerfield.AbstractPlannerField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldDayForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldDayForm.MainBox.TabBox;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldDayForm.MainBox.TabBox.PlannerField1Box;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldDayForm.MainBox.TabBox.PlannerField1Box.PlannerField;

public class PlannerFieldDayForm extends AbstractForm implements IPageForm {

  public PlannerFieldDayForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "PlannerField Day";
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public PlannerField getPlannerField() {
    return getFieldByClass(PlannerField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public PlannerField1Box getPlannerField1Box() {
    return getFieldByClass(PlannerField1Box.class);
  }

  public TabBox getTabBox() {
    return getFieldByClass(TabBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredWidthInPixel() {
      return 1200;
    }

    @Order(10.0)
    public class TabBox extends AbstractTabBox {

      @Order(10.0)
      public class PlannerField1Box extends AbstractGroupBox {

        @Override
        protected String getConfiguredLabel() {
          return "PlannerField Day";
        }

        @Order(20.0)
        public class PlannerField extends AbstractPlannerField<PlannerField.ResourceTable, PlannerField.ActivityMap, Long, Long> {

          private static final int NUM_DAYS = 7;

          @Override
          protected int getConfiguredGridH() {
            return 11;
          }

          @Override
          protected int getConfiguredGridW() {
            return 2;
          }

          @Override
          protected int getConfiguredMiniCalendarCount() {
            return 1;
          }

          @Override
          protected void execInitField() throws ProcessingException {
            getActivityMap().setPlanningMode(IActivityMap.PLANNING_MODE_DAY);
            initDays();
            loadResourceTableData();
          }

          private void initDays() {
            ArrayList<Date> days = new ArrayList<Date>();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // get start of this week in milliseconds
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

            for (int i = 0; i < NUM_DAYS; i++) {
              days.add(cal.getTime());
              cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            getActivityMap().setDays(days.toArray(new Date[days.size()]));
          }

          private Date[] getStartEndTime(final Long resourceId) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // get start of this week in milliseconds
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

            Date[] startEndTime = new Date[2];
            if (resourceId == 0) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 0);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 1) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 2) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 2);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 3) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 3);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 4) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 4);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 5) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 5);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 6) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 6);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 7) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 2);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 3);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 8) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 5);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 9) {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 2);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              startEndTime[1] = cal.getTime();
            }
            else {
              //2  startTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 0);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.DAY_OF_MONTH, 1);
              cal.add(Calendar.MILLISECOND, -1);
              startEndTime[1] = cal.getTime();
            }
            return startEndTime;
          }

          @Override
          protected Object[][] execLoadResourceTableData() throws ProcessingException {
            Object[][] data = new Object[10][2];
            for (int i = 0; i < data.length; i++) {
              data[i][0] = i;
              data[i][1] = "Name " + i;
            }
            data[0][1] = "SUN";
            data[1][1] = "MON";
            data[2][1] = "TUE / In the Gap";
            data[3][1] = "WED / In the Gap";
            data[4][1] = "THU / In the Gap";
            data[5][1] = "FRI";
            data[6][1] = "SAT";
            data[7][1] = "TUE,WED,THU / In the Gap";
            data[8][1] = "MON,TUE,WED,THU,FRI / Over the Gap";
            data[9][1] = "TUE 23.59.59.999, WED / Over the Gap";
            return data;
          }

          @Override
          protected Object[][] execLoadActivityMapData(List<? extends Long> resourceIds, List<? extends ITableRow> resourceRows) throws ProcessingException {
            Object[][] data = new Object[resourceIds.size()][9];
            int dataIndex = 0;
            for (int rowIndex = 0; rowIndex < resourceIds.size(); rowIndex++) {
              createEvent(resourceIds.get(rowIndex), data, dataIndex, rowIndex);
              // next
              dataIndex++;
            }
            return data;
          }

          private void createEvent(final Long resourceId, final Object[][] data, final int dataIndex, final int rowIndex) {
            //0  resourceId of type RI
            data[dataIndex][0] = resourceId;

            //1  activityId of type AI
            data[dataIndex][1] = dataIndex;
            Date[] startEndTime = getStartEndTime(resourceId);
            //2  startTime of type Date
            data[dataIndex][2] = startEndTime[0];
            //3  endTime of type Date
            data[dataIndex][3] = startEndTime[1];

            //4  text of type String
            //5  tooltipText of type String
            //6  iconId of type String

            //7  majorValue of type Number
            data[dataIndex][7] = 1d;

            //8  minorValue of type Number
            data[dataIndex][8] = 1d;

          }

          @Order(10.0)
          public class ResourceTable extends AbstractTable {

            @Override
            protected boolean getConfiguredAutoResizeColumns() {
              return true;
            }

            @Override
            protected boolean getConfiguredSortEnabled() {
              return false;
            }

            @Override
            protected void execRowAction(ITableRow row) throws ProcessingException {
              loadActivityMapDataOfSelectedRecources();
            }

            public NameColumn getNameColumn() {
              return getColumnSet().getColumnByClass(NameColumn.class);
            }

            public ResourceIdColumn getResourceIdColumn() {
              return getColumnSet().getColumnByClass(ResourceIdColumn.class);
            }

            @Order(10.0f)
            public class ResourceIdColumn extends AbstractLongColumn {

              @Override
              protected boolean getConfiguredPrimaryKey() {
                return true;
              }
            }

            @Order(20.0f)
            public class NameColumn extends AbstractStringColumn {
            }
          }

          @Order(10.0)
          public class ActivityMap extends AbstractActivityMap<Long, Long> {

            @Override
            protected int getConfiguredFirstHourOfDay() {
              return 0;
            }

            @Override
            protected int getConfiguredLastHourOfDay() {
              return 23;
            }

            @Override
            protected boolean getConfiguredDrawSections() {
              return false;
            }

            @Override
            protected void execDecorateActivityCell(ActivityCell cell) throws ProcessingException {
              cell.setMajorColor("FE9915");
              cell.setMinorColor("FE9915");
//              cell.setMinorColor("046989");
            }
          }

        }

        @Order(30.0)
        public class WorkDayOnlyButton extends AbstractButton {

          @Override
          protected String getConfiguredLabel() {
            return "Hide TUE, WED and THU";
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            ArrayList<Date> days = new ArrayList<Date>();
            Date[] oldDays = getPlannerField().getActivityMap().getDays();
            for (Date d : oldDays) {
              Calendar cal = Calendar.getInstance();
              cal.setTime(d);
              if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY
                  && cal.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                days.add(d);
              }

            }
            getPlannerField().getActivityMap().setDays(days.toArray(new Date[days.size()]));
            getPlannerField().setMiniCalendarCount(0);
            getPlannerField().setMiniCalendarCount(1);
          }
        }
      }
    }

    @Order(20.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
