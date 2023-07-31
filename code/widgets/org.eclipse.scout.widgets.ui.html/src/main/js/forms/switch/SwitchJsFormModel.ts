/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Button, CheckBoxField, FormFieldMenu, FormModel, GroupBox, Notification, SequenceBox, SmartField, Status, StringField, Switch, TabBox, TabItem, WidgetField} from '@eclipse-scout/core';
import {SwitchDisplayStyleLookupCall} from '../../index';

export default (): FormModel => ({
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    menus: [
      {
        id: 'ShowAllCombinationsMenu',
        objectType: FormFieldMenu,
        horizontalAlignment: 1,
        field: {
          id: 'ShowAllCombinationsField',
          objectType: CheckBoxField,
          cssClass: 'no-mandatory-indicator',
          labelVisible: false,
          statusVisible: false,
          label: 'Show all combinations'
        }
      }
    ],
    fields: [
      {
        // Additional box to increase the padding
        id: 'NotificationBox',
        objectType: GroupBox,
        cssClass: 'top-padding-invisible',
        notification: {
          objectType: Notification,
          message: 'The "Switch" widget is currently only available in Scout JS.',
          severity: Status.Severity.INFO
        }
      },
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        fields: [
          {
            id: 'WidgetField',
            objectType: WidgetField,
            labelVisible: false,
            statusVisible: false,
            scrollable: false,
            gridDataHints: {
              useUiHeight: true,
              fillHorizontal: false,
              horizontalAlignment: 0
            },
            fieldWidget: {
              id: 'Switch',
              objectType: Switch,
              label: 'Switch'
            }
          }
        ]
      },
      {
        // This wrapper sequence box is needed for the proper right margin (statusVisible=true)
        id: 'AllCombinationsSequenceBox',
        objectType: SequenceBox,
        labelVisible: false,
        gridDataHints: {
          useUiHeight: true,
          w: GroupBox.FULL_WIDTH
        },
        fields: [
          {
            id: 'AllCombinationsBox',
            objectType: GroupBox,
            gridColumnCount: 8,
            bodyLayoutConfig: {
              hgap: 0
            },
            label: 'All Combinations'
          }
        ]
      },
      {
        id: 'ConfigurationBox',
        objectType: TabBox,
        cssClass: 'jswidgets-configuration',
        selectedTab: 'PropertiesTab',
        tabItems: [
          {
            id: 'PropertiesTab',
            objectType: TabItem,
            label: 'Properties',
            fields: [
              {
                id: 'PropertiesBox',
                objectType: GroupBox,
                label: 'Properties',
                labelVisible: false,
                borderVisible: false,
                fields: [
                  {
                    id: 'PropertiesLeftBox',
                    objectType: GroupBox,
                    borderVisible: false,
                    gridDataHints: {
                      w: 1
                    },
                    gridColumnCount: 1,
                    fields: [
                      {
                        id: 'EnabledField',
                        objectType: CheckBoxField,
                        label: 'Enabled',
                        labelVisible: false
                      },
                      {
                        id: 'ActivatedField',
                        objectType: CheckBoxField,
                        label: 'Activated',
                        labelVisible: false
                      },
                      {
                        id: 'LabelHtmlEnabledField',
                        objectType: CheckBoxField,
                        label: 'Label Html Enabled',
                        labelVisible: false
                      },
                      {
                        id: 'LabelVisibleField',
                        objectType: CheckBoxField,
                        label: 'Label Visible',
                        labelVisible: false,
                        triStateEnabled: true
                      },
                      {
                        id: 'IconVisibleField',
                        objectType: CheckBoxField,
                        label: 'Icon Visible',
                        labelVisible: false
                      },
                      {
                        id: 'TabbableBox',
                        objectType: SequenceBox,
                        labelVisible: false,
                        fields: [
                          {
                            id: 'TabbableField',
                            objectType: CheckBoxField,
                            label: 'Tabbable',
                            labelVisible: false,
                            statusVisible: false,
                            gridDataHints: {
                              useUiWidth: true,
                              weightX: 0
                            }
                          },
                          {
                            id: 'FocusButton',
                            objectType: Button,
                            label: 'Focus',
                            displayStyle: Button.DisplayStyle.LINK,
                            processButton: false,
                            visible: false
                          }
                        ]
                      }
                    ]
                  },
                  {
                    id: 'PropertiesRightBox',
                    objectType: GroupBox,
                    borderVisible: false,
                    gridDataHints: {
                      w: 1
                    },
                    gridColumnCount: 1,
                    fields: [
                      {
                        id: 'LabelField',
                        objectType: StringField,
                        label: 'Label'
                      },
                      {
                        id: 'TooltipTextField',
                        objectType: StringField,
                        label: 'Tooltip Text'
                      },
                      {
                        id: 'DisplayStyleField',
                        objectType: SmartField,
                        label: 'Display Style',
                        lookupCall: SwitchDisplayStyleLookupCall,
                        displayStyle: SmartField.DisplayStyle.DROPDOWN
                      },
                      {
                        id: 'PreventDefaultField',
                        objectType: CheckBoxField,
                        label: 'Prevent "switch" Event',
                        labelVisible: false
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ]
      }
    ]
  }
})
;

/* **************************************************************************
* GENERATED WIDGET MAPS
* **************************************************************************/

export type SwitchJsFormWidgetMap = {
  'MainBox': GroupBox;
  'ShowAllCombinationsMenu': FormFieldMenu;
  'ShowAllCombinationsField': CheckBoxField;
  'NotificationBox': GroupBox;
  'DetailBox': GroupBox;
  'WidgetField': WidgetField;
  'Switch': Switch;
  'AllCombinationsSequenceBox': SequenceBox;
  'AllCombinationsBox': GroupBox;
  'ConfigurationBox': TabBox;
  'PropertiesTab': TabItem;
  'PropertiesBox': GroupBox;
  'PropertiesLeftBox': GroupBox;
  'EnabledField': CheckBoxField;
  'ActivatedField': CheckBoxField;
  'LabelHtmlEnabledField': CheckBoxField;
  'LabelVisibleField': CheckBoxField;
  'IconVisibleField': CheckBoxField;
  'TabbableBox': SequenceBox;
  'TabbableField': CheckBoxField;
  'FocusButton': Button;
  'PropertiesRightBox': GroupBox;
  'LabelField': StringField;
  'TooltipTextField': StringField;
  'DisplayStyleField': SmartField<any>;
  'PreventDefaultField': CheckBoxField;
};
