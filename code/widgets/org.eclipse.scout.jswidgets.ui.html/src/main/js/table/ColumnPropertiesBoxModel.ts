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
import {CheckBoxField, GroupBox, GroupBoxModel, NumberField, SmartField, StringField} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  id: 'jswidgets.ColumnPropertiesBox',
  objectType: GroupBox,
  gridColumnCount: 2,
  label: 'Column Properties',
  expandable: true,
  fields: [
    {
      id: 'EditableField',
      objectType: CheckBoxField,
      label: 'Editable',
      labelVisible: false
    },
    {
      id: 'DisplayableField',
      objectType: CheckBoxField,
      label: 'Displayable',
      labelVisible: false
    },
    {
      id: 'VisibleField',
      objectType: CheckBoxField,
      label: 'Visible',
      labelVisible: false
    },
    {
      id: 'MandatoryField',
      objectType: CheckBoxField,
      label: 'Mandatory',
      labelVisible: false
    },
    {
      id: 'AutoOptimizeWidthField',
      objectType: CheckBoxField,
      label: 'Auto Optimize Width',
      labelVisible: false
    },
    {
      id: 'FixedWidthField',
      objectType: CheckBoxField,
      label: 'Fixed Width',
      labelVisible: false
    },
    {
      id: 'FixedPositionField',
      objectType: CheckBoxField,
      label: 'Fixed Position',
      labelVisible: false
    },
    {
      id: 'HeaderMenuEnabledField',
      objectType: CheckBoxField,
      label: 'Header Menu Enabled',
      labelVisible: false
    },
    {
      id: 'HeaderHtmlEnabledField',
      objectType: CheckBoxField,
      label: 'Header Html Enabled',
      labelVisible: false
    },
    {
      id: 'HtmlEnabledField',
      objectType: CheckBoxField,
      label: 'Html Enabled',
      labelVisible: false,
      enabled: false
    },
    {
      id: 'HeaderTooltipHtmlEnabledField',
      objectType: CheckBoxField,
      label: 'Header Tooltip Html Enabled',
      labelVisible: false
    },
    {
      id: 'TextWrapField',
      objectType: CheckBoxField,
      label: 'Text Wrap',
      labelVisible: false
    },
    {
      id: 'SortActiveField',
      objectType: CheckBoxField,
      label: 'Sort Active',
      labelVisible: false,
      enabled: false
    },
    {
      id: 'SortAscendingField',
      objectType: CheckBoxField,
      label: 'Sort Ascending',
      labelVisible: false,
      enabled: false
    },
    {
      id: 'GroupedField',
      objectType: CheckBoxField,
      label: 'Grouped',
      labelVisible: false,
      enabled: false
    },
    {
      id: 'ModifiableField',
      objectType: CheckBoxField,
      label: 'Modifiable',
      labelVisible: false,
      enabled: false
    },
    {
      id: 'RemovableField',
      objectType: CheckBoxField,
      label: 'Removable',
      labelVisible: false,
      enabled: false
    },
    {
      id: 'CssClassField',
      objectType: StringField,
      label: 'Css Class'
    },
    {
      id: 'AutoOptimizeMaxWidthField',
      objectType: NumberField,
      label: 'Auto Optimize Max Width',
      enabled: false
    },
    {
      id: 'HorizontalAlignmentField',
      objectType: NumberField,
      label: 'Horizontal Alignment'
    },
    {
      id: 'TextField',
      objectType: StringField,
      label: 'Text'
    },
    {
      id: 'HeaderTooltipTextField',
      objectType: StringField,
      label: 'Header Tooltip Text'
    },
    {
      id: 'HeaderIconIdField',
      objectType: SmartField,
      lookupCall: 'jswidgets.IconIdLookupCall',
      label: 'Header Icon Id'
    },
    {
      id: 'HeaderCssClassField',
      objectType: StringField,
      label: 'Header Css Class'
    },
    {
      id: 'SortIndexField',
      objectType: NumberField,
      label: 'Sort Index',
      enabled: false
    },
    {
      id: 'WidthField',
      objectType: NumberField,
      label: 'Width',
      enabled: true
    },
    {
      id: 'MinWidthField',
      objectType: NumberField,
      label: 'Min Width',
      enabled: false
    },
    {
      id: 'MaxLengthField',
      objectType: NumberField,
      label: 'Max Length'
    }
  ]
});

export type ColumnPropertiesBoxWidgetMap = {
  'EditableField': CheckBoxField;
  'DisplayableField': CheckBoxField;
  'VisibleField': CheckBoxField;
  'MandatoryField': CheckBoxField;
  'AutoOptimizeWidthField': CheckBoxField;
  'FixedWidthField': CheckBoxField;
  'FixedPositionField': CheckBoxField;
  'HeaderMenuEnabledField': CheckBoxField;
  'HeaderHtmlEnabledField': CheckBoxField;
  'HtmlEnabledField': CheckBoxField;
  'HeaderTooltipHtmlEnabledField': CheckBoxField;
  'TextWrapField': CheckBoxField;
  'SortActiveField': CheckBoxField;
  'SortAscendingField': CheckBoxField;
  'GroupedField': CheckBoxField;
  'ModifiableField': CheckBoxField;
  'RemovableField': CheckBoxField;
  'CssClassField': StringField;
  'AutoOptimizeMaxWidthField': NumberField;
  'HorizontalAlignmentField': NumberField;
  'TextField': StringField;
  'HeaderTooltipTextField': StringField;
  'HeaderIconIdField': SmartField<string>;
  'HeaderCssClassField': StringField;
  'SortIndexField': NumberField;
  'WidthField': NumberField;
  'MinWidthField': NumberField;
  'MaxLengthField': NumberField;
};
