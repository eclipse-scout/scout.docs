package org.eclipse.scout.widgets.client;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

public final class WidgetsClientConfigProperties {

  public static class GitUrlConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "git.url";
    }

    @Override
    protected String getDefaultValue() {
      return "https://github.com/bsi-software/org.eclipse.scout.docs/blob";
    }
  }

  public static class GitBranchConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "git.branch";
    }

    @Override
    protected String getDefaultValue() {
      return "releases/6.0.x";
    }
  }

  public static class GitFolderConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "git.folder";
    }

    @Override
    protected String getDefaultValue() {
      return "code/widgets";
    }
  }

  public static class GitSourceConfigProperty extends AbstractStringConfigProperty {

    @Override
    public String getKey() {
      return "git.source";
    }

    @Override
    protected String getDefaultValue() {
      return "src/main/java";
    }
  }
}
