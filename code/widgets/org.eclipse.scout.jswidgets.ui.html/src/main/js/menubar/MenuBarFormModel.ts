/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {CheckBoxField, ComboMenu, FormField, FormFieldMenu, FormMenu, FormModel, GroupBox, icons, LabelField, Menu, SmartField, SubMenuVisibility, TabBox, TabItem} from '@eclipse-scout/core';
import {
  ActionPropertiesBox, ActionPropertiesBoxWidgetMap, EventsTab, EventsTabWidgetMap, FormFieldPropertiesBox, FormFieldPropertiesBoxWidgetMap, GroupBoxAddMenuBox, GroupBoxAddMenuBoxWidgetMap, GroupBoxDeleteMenuBox,
  GroupBoxDeleteMenuBoxWidgetMap, MenuActionsBox, MenuActionsBoxWidgetMap, MiniForm, WidgetActionsBox, WidgetActionsBoxWidgetMap
} from '../index';

export default (): FormModel => ({
  id: 'jswidgets.MenuBarForm',
  displayHint: 'view',
  rootGroupBox: {
    id: 'MainBox',
    objectType: GroupBox,
    fields: [
      {
        id: 'DetailBox',
        objectType: GroupBox,
        gridColumnCount: 1,
        fields: [
          {
            id: 'LabelField',
            objectType: LabelField,
            labelVisible: false,
            value: 'This is a group box containing menus. The menus are displayed in the menu bar on top.\nCheck out the properties on the bottom to configure each menu individually.',
            gridDataHints: {
              h: 2,
              weightY: 0
            }
          }
        ],
        menus: [
          {
            id: 'Menu1',
            objectType: Menu,
            text: 'First menu'
          },
          {
            id: 'Menu2',
            objectType: Menu,
            text: 'Second menu',
            iconId: icons.WORLD
          },
          {
            id: 'HierarchicalMenu',
            objectType: Menu,
            text: 'Hierarchical menu',
            childActions: [
              {
                id: 'ReplaceMenu',
                objectType: Menu,
                text: 'Replace child menus'
              },
              {
                id: 'SubMenu1',
                objectType: Menu,
                text: 'Sub Menu 1'
              },
              {
                id: 'SubMenu2',
                objectType: Menu,
                text: 'Sub Menu 2',
                childActions: [
                  {
                    id: 'SubSubMenu1',
                    objectType: Menu,
                    text: 'Sub Sub Menu 1'
                  },
                  {
                    id: 'SubSubMenu2',
                    objectType: Menu,
                    text: 'Sub Sub Menu 2',
                    childActions: [{
                      id: 'SubSubSubMenu1',
                      objectType: Menu,
                      iconId: icons.WORLD,
                      text: 'Menu with Icon'
                    }, {
                      id: 'SubSubSubMenuMenu2',
                      objectType: Menu,
                      text: 'More',
                      childActions: [{
                        id: 'SubSubSubSubMenu1',
                        objectType: Menu,
                        text: 'Deep Menu'
                      }]
                    }]
                  }
                ]
              }
            ]
          },
          {
            id: 'FormMenu',
            objectType: FormMenu,
            text: 'Form menu',
            form: {
              objectType: MiniForm
            }
          },
          {
            id: 'ComboMenu',
            objectType: ComboMenu,
            childActions: [{
              id: 'ComboMenuChild1',
              objectType: Menu,
              text: 'Combo menu'
            }, {
              id: 'ComboMenuChild2',
              objectType: Menu,
              subMenuVisibility: Menu.SubMenuVisibility.ALWAYS,
              childActions: [{
                objectType: Menu,
                text: 'Child menu 1'
              }, {
                objectType: Menu,
                text: 'Child menu 2'
              }]
            }]
          },
          {
            id: 'FormFieldMenu',
            objectType: FormFieldMenu,
            horizontalAlignment: 1,
            field: {
              id: 'SmartField',
              objectType: SmartField,
              label: 'Form Field Menu',
              lookupCall: 'jswidgets.LocaleLookupCall',
              labelVisible: false,
              statusVisible: false,
              labelPosition: FormField.LabelPosition.ON_FIELD,
              gridDataHints: {
                widthInPixel: 200
              }
            }
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
                    id: 'SelectedMenuField',
                    objectType: SmartField,
                    label: 'Target',
                    lookupCall: 'jswidgets.MenuItemLookupCall'
                  },
                  {
                    id: 'MenuPropertiesBox',
                    objectType: GroupBox,
                    labelVisible: false,
                    borderVisible: false,
                    fields: [
                      {
                        id: 'ShrinkableField',
                        objectType: CheckBoxField,
                        label: 'Shrinkable',
                        labelVisible: false,
                        tooltipText: '${textKey:MenuShrinkableTooltipText}',
                        gridDataHints: {
                          fillHorizontal: false
                        }
                      },
                      {
                        id: 'StackableField',
                        objectType: CheckBoxField,
                        label: 'Stackable',
                        labelVisible: false,
                        tooltipText: '${textKey:MenuStackableTooltipText}',
                        gridDataHints: {
                          fillHorizontal: false
                        }
                      },
                      {
                        id: 'SubMenuVisibilityField',
                        objectType: SmartField,
                        lookupCall: 'jswidgets.SubMenuVisibilityLookupCall',
                        label: 'Sub Menu Visibility'
                      }
                    ]
                  },
                  {
                    id: 'ActionPropertiesBox',
                    objectType: ActionPropertiesBox
                  },
                  {
                    id: 'FormFieldPropertiesBox',
                    objectType: FormFieldPropertiesBox
                  }
                ]
              }
            ]
          },
          {
            id: 'ActionsTab',
            objectType: TabItem,
            label: 'Actions',
            fields: [
              {
                id: 'ActionTargetField',
                objectType: SmartField,
                label: 'Target',
                lookupCall: 'jswidgets.MenuItemLookupCall'
              },
              {
                id: 'Actions.MenuActionsBox',
                objectType: MenuActionsBox
              },
              {
                id: 'Actions.AddGroupBoxMenuBox',
                objectType: GroupBoxAddMenuBox
              },
              {
                id: 'Actions.DeleteGroupBoxMenuBox',
                objectType: GroupBoxDeleteMenuBox
              },
              {
                id: 'WidgetActionsBox',
                objectType: WidgetActionsBox
              }
            ]
          },
          {
            id: 'EventsTab',
            objectType: EventsTab
          }
        ]
      }
    ]
  }
});

export type MenuBarFormWidgetMap =
  {
    'MainBox': GroupBox;
    'DetailBox': GroupBox;
    'LabelField': LabelField;
    'Menu1': Menu;
    'Menu2': Menu;
    'HierarchicalMenu': Menu;
    'ReplaceMenu': Menu;
    'SubMenu1': Menu;
    'SubMenu2': Menu;
    'SubSubMenu1': Menu;
    'SubSubMenu2': Menu;
    'SubSubSubMenu1': Menu;
    'SubSubSubMenuMenu2': Menu;
    'SubSubSubSubMenu1': Menu;
    'FormMenu': FormMenu;
    'ComboMenu': ComboMenu;
    'ComboMenuChild1': Menu;
    'ComboMenuChild2': Menu;
    'FormFieldMenu': FormFieldMenu;
    'SmartField': SmartField<string>;
    'ConfigurationBox': TabBox;
    'PropertiesTab': TabItem;
    'PropertiesBox': GroupBox;
    'SelectedMenuField': SmartField<Menu>;
    'MenuPropertiesBox': GroupBox;
    'ShrinkableField': CheckBoxField;
    'StackableField': CheckBoxField;
    'SubMenuVisibilityField': SmartField<SubMenuVisibility>;
    'ActionPropertiesBox': ActionPropertiesBox;
    'FormFieldPropertiesBox': FormFieldPropertiesBox;
    'ActionsTab': TabItem;
    'ActionTargetField': SmartField<Menu>;
    'Actions.MenuActionsBox': MenuActionsBox;
    'Actions.AddGroupBoxMenuBox': GroupBoxAddMenuBox;
    'Actions.DeleteGroupBoxMenuBox': GroupBoxDeleteMenuBox;
    'WidgetActionsBox': WidgetActionsBox;
    'EventsTab': EventsTab;
  }
  & ActionPropertiesBoxWidgetMap & FormFieldPropertiesBoxWidgetMap & MenuActionsBoxWidgetMap & GroupBoxAddMenuBoxWidgetMap & GroupBoxDeleteMenuBoxWidgetMap
  & WidgetActionsBoxWidgetMap & EventsTabWidgetMap;
