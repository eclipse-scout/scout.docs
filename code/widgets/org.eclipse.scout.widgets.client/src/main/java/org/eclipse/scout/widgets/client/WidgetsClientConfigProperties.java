package org.eclipse.scout.widgets.client;

import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;

/**
 * <h3>{@link WidgetsClientConfigProperties}</h3>
 */
public final class WidgetsClientConfigProperties {
  public static class GitUrlConfigProperty extends AbstractStringConfigProperty {
    @Override
    public String getKey() {
      return "git.url";
    }

    @Override
    protected String getDefaultValue() {
      return "https://github.com/BSI-Business-Systems-Integration-AG/org.eclipse.scout.docs/blob";
    }
  }

  public static class GitBranchConfigProperty extends AbstractStringConfigProperty {
    @Override
    public String getKey() {
      return "git.branch";
    }

    @Override
    protected String getDefaultValue() {
      return "releases/7.1.x";
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
