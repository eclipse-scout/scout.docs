/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.jswidgets.rest;

import jakarta.annotation.Generated;

import org.eclipse.scout.rt.dataobject.DoEntity;
import org.eclipse.scout.rt.dataobject.DoValue;

public class SampleUiNotificationPutRequest extends DoEntity {

  public DoValue<DoEntity> message() {
    return doValue("message");
  }

  public DoValue<String> topic() {
    return doValue("topic");
  }

  /* **************************************************************************
   * GENERATED CONVENIENCE METHODS
   * *************************************************************************/

  @Generated("DoConvenienceMethodsGenerator")
  public SampleUiNotificationPutRequest withMessage(DoEntity message) {
    message().set(message);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public DoEntity getMessage() {
    return message().get();
  }

  @Generated("DoConvenienceMethodsGenerator")
  public SampleUiNotificationPutRequest withTopic(String topic) {
    topic().set(topic);
    return this;
  }

  @Generated("DoConvenienceMethodsGenerator")
  public String getTopic() {
    return topic().get();
  }
}
