/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// @ts-expect-error
//tag::ExposedCodeType[]
import {Code, CodeType} from '@eclipse-scout/core';

export class ExposedCodeType extends CodeType<string> {
  first: Code<string>; // <1>
  second: Code<string>; // <1>
}
//end::ExposedCodeType[]
