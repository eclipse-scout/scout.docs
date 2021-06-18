/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public final class WidgetsClientConfigProperties {

  private WidgetsClientConfigProperties() {
  }

  public static class GitUrlConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.url";
    }

    @Override
    public String description() {
      return "Base URL that points to the source code of the widgets app on github.";
    }

    @Override
    public String getDefaultValue() {
      return "https://github.com/bsi-software/org.eclipse.scout.docs/blob";
    }
  }

  public static class GitBranchConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.branch";
    }

    @Override
    public String description() {
      return "Git branch that is used to build the URL to open the source code on github.";
    }

    @Override
    public String getDefaultValue() {
      return "releases/10.0";
    }
  }

  public static class GitFolderConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.folder";
    }

    @Override
    public String description() {
      return "Git subfolder that is used to build the URL to open the source code on github.";
    }

    @Override
    public String getDefaultValue() {
      return "code/widgets";
    }
  }

  public static class GitSourceConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "widgets.git.source";
    }

    @Override
    public String description() {
      return "Git source folder that is used to build the URL to open the source code on github.";
    }

    @Override
    public String getDefaultValue() {
      return "src/main/java";
    }
  }

  public static class ReadOnlyProperty extends AbstractBooleanConfigProperty {

    @Override
    public String getKey() {
      return "widgets.readOnly";
    }

    @Override
    public String description() {
      return "Global flag to activate a read-only mode when widgets application is deployed as public available demo application";
    }

    @Override
    public Boolean getDefaultValue() {
      return false;
    }
  }

  public static class SeleniumProperty extends AbstractBooleanConfigProperty {

    @Override
    public String getKey() {
      return "widgets.selenium";
    }

    @Override
    public String description() {
      return "Flag to make UI elements required only for Selenium tests, visible in the forms of the widget application. " +
          "They should not be visible in a productive deployment. The value default is false (=invisible).";
    }

    @Override
    public Boolean getDefaultValue() {
      return false;
    }
  }
}
