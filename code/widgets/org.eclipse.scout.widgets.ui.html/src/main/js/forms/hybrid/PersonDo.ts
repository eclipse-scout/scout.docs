/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

import {dates, objects, strings} from '@eclipse-scout/core';

export class PersonDo {
  _type: 'widgets.Person';
  name: string;
  firstName: string;
  dateOfBirth: Date;
  active: boolean;

  constructor() {
    this._type = 'widgets.Person';
  }

  toJson(): object {
    const result = {};
    Object.keys(this).forEach(key => (result[key] = this._propertyToJson(this[key])));
    return result;
  }

  protected _propertyToJson(property: any): any {
    if (property instanceof Date) {
      return dates.toJsonDate(property);
    }
    return property;
  }

  static of(stringOrObject: string | object): PersonDo {
    if (!stringOrObject) {
      return null;
    }
    if (objects.isString(stringOrObject)) {
      stringOrObject = JSON.parse(stringOrObject);
    }
    if (!objects.isPlainObject(stringOrObject)) {
      return null;
    }
    const personDo = new PersonDo();
    personDo.name = this._parseString(stringOrObject, 'name');
    personDo.firstName = this._parseString(stringOrObject, 'firstName');
    personDo.dateOfBirth = this._parseDate(stringOrObject, 'dateOfBirth');
    personDo.active = this._parseBoolean(stringOrObject, 'active');
    return personDo;
  }

  protected static _parseString(o: object, property: string): string {
    return strings.asString(o[property]);
  }

  protected static _parseBoolean(o: object, property: string): boolean {
    return o ? !!o[property] : null;
  }

  protected static _parseDate(o: object, property: string): Date {
    if (!o) {
      return null;
    }
    const candidate = o[property];
    if (candidate instanceof Date) {
      return candidate;
    }
    if (objects.isString(candidate)) {
      return dates.parseJsonDate(candidate);
    }
    return null;
  }
}
