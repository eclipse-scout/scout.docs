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
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.plannerfield.AbstractPlannerField;
import org.eclipse.scout.rt.client.ui.form.fields.tabbox.AbstractTabBox;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldIntradayForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldIntradayForm.MainBox.TabBox;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldIntradayForm.MainBox.TabBox.PlannerField1Box;
import org.eclipsescout.demo.widgets.client.ui.forms.PlannerFieldIntradayForm.MainBox.TabBox.PlannerField1Box.IntradayPlannerField;

public class PlannerFieldIntradayForm extends AbstractForm implements IPageForm {

  public PlannerFieldIntradayForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return "PlannerField Intraday";
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public IntradayPlannerField getIntradayPlannerField() {
    return getFieldByClass(IntradayPlannerField.class);
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
          return "PlannerField Intraday";
        }

        @Order(20.0)
        public class IntradayPlannerField extends AbstractPlannerField<IntradayPlannerField.ResourceTable, IntradayPlannerField.ActivityMap, Long, Long> {

          private static final int NUM_DAYS = 2;

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
            getActivityMap().setPlanningMode(IActivityMap.PLANNING_MODE_INTRADAY);
            ArrayList<Date> days = new ArrayList<Date>();
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < NUM_DAYS; i++) {
              days.add(cal.getTime());
              cal.add(Calendar.DAY_OF_YEAR, 1);
            }
            getActivityMap().setDays(days.toArray(new Date[0]));
            loadResourceTableData();
          }

          private Date[] getStartEndTime(final Long resourceId, final int day) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, day);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date[] startEndTime = new Date[2];
            if (resourceId == 0) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 22);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 2);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 1) {
              //2  startTime of type Date
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 2);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 2) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 22);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 4);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 3) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, -2);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 4);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 4) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 15);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 19);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 5) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 6);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 4);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 6) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 8);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 2);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 7) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 15);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 4);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 8) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 15);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 2);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 9) {
              //2  startTime of type Date
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 24);
              startEndTime[1] = cal.getTime();
            }
            else if (resourceId == 10) {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 11);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 2);
              startEndTime[1] = cal.getTime();
            }
            else {
              //2  startTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 14);
              startEndTime[0] = cal.getTime();
              //3  endTime of type Date
              cal.add(Calendar.HOUR_OF_DAY, 2);
              startEndTime[1] = cal.getTime();
            }
            return startEndTime;
          }

          @Override
          protected Object[][] execLoadResourceTableData() throws ProcessingException {
            Object[][] data = new Object[11][2];
            for (int i = 0; i < data.length; i++) {
              data[i][0] = i;
              data[i][1] = "Name " + i;
            }
            data[0][1] = "22-24 Until Midnight / In the Gap";
            data[1][1] = "00-02 From Midnight / In the Gap";
            data[2][1] = "22-02 Over Midnight (r) / In the Gap";
            data[3][1] = "22-02 Over Midnight (l) / In the Gap";
            data[4][1] = "15-10 Over Midnight (r) / Over the Gap (l&r)";
            data[5][1] = "06-10 Over the Gap (l)";
            data[6][1] = "08-10 Touching the Gap (l)";
            data[7][1] = "15-19 Over the Gap (r)";
            data[8][1] = "15-17 Touching the Gap (r)";
            data[9][1] = "00-24 Whole Day";
            data[10][1] = "11-13 In the Middle";
            return data;
          }

          @Override
          protected Object[][] execLoadActivityMapData(List<? extends Long> resourceIds, List<? extends ITableRow> resourceRows) throws ProcessingException {
            Object[][] data = new Object[resourceIds.size() * NUM_DAYS][9];
            int dataIndex = 0;
            for (int rowIndex = 0; rowIndex < resourceIds.size(); rowIndex++) {
//              Calendar cal = Calendar.getInstance();
//              cal.setTime(getActivityMap().getBeginTime());
              for (int day = 0; day < NUM_DAYS; day++) {
                createEvent(resourceIds.get(rowIndex), data, dataIndex, rowIndex, day);

                // next
                dataIndex++;

              }
            }
            return data;
          }

          private void createEvent(final Long resourceId, final Object[][] data, final int dataIndex, final int rowIndex, final int day) {
            //0  resourceId of type RI
            data[dataIndex][0] = resourceId;

            //1  activityId of type AI
            data[dataIndex][1] = dataIndex;
            Date[] startEndTime = getStartEndTime(resourceId, day);
            //2  startTime of type Date
            data[dataIndex][2] = startEndTime[0];
            //3  endTime of type Date
            data[dataIndex][3] = startEndTime[1];

            //4  text of type String
            //5  tooltipText of type String
            //6  iconId of type String

            //7  majorValue of type Number
//              data[dataIndex][7] = maj;
            data[dataIndex][7] = 1d - day * 0.5d;

            //8  minorValue of type Number
//              data[dataIndex][8] = min;
            data[dataIndex][8] = 1d - day * 0.5d;

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
        public class FullDayField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return "Whole Day";
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(true);
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            if (getValue()) {
              getIntradayPlannerField().getActivityMap().setFirstHourOfDay(0);
              getIntradayPlannerField().getActivityMap().setLastHourOfDay(23);
            }
            else {
              getIntradayPlannerField().getActivityMap().setFirstHourOfDay(8);
              getIntradayPlannerField().getActivityMap().setLastHourOfDay(16);
            }
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
