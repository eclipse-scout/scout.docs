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
package org.eclipsescout.demo.minifigcreator.shared.services.process;

import java.io.Serializable;

/**
 * Bean to materialize the state of the fields containing parts.
 */
public class FormState implements Serializable {
  private static final long serialVersionUID = 1L;
  private boolean headEnabled;
  private boolean torsoEnabled;
  private boolean legsEnabled;

  public boolean isHeadEnabled() {
    return headEnabled;
  }

  public void setHeadEnabled(boolean headEnabled) {
    this.headEnabled = headEnabled;
  }

  public boolean isTorsoEnabled() {
    return torsoEnabled;
  }

  public void setTorsoEnabled(boolean torsoEnabled) {
    this.torsoEnabled = torsoEnabled;
  }

  public boolean isLegsEnabled() {
    return legsEnabled;
  }

  public void setLegsEnabled(boolean legsEnabled) {
    this.legsEnabled = legsEnabled;
  }

}
