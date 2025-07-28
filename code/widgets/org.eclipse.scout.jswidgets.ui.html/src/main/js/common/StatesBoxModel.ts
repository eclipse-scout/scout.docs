/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ChildModelOf, FormField, GroupBox, GroupBoxModel} from '@eclipse-scout/core';

export default (): GroupBoxModel => ({
  objectType: GroupBox,
  borderVisible: false,
  gridColumnCount: 1,
  responsive: false,
  visible: false
});

export interface StatesBoxModel extends GroupBoxModel {
  field?: FormField;
  fieldModel?: ChildModelOf<FormField>;
  fieldInitProperties?: string[];
}
