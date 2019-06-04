package org.eclipse.scout.widgets.client;

public final class WidgetsClientConfigProperties {

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
      return "https://github.com/BSI-Business-Systems-Integration-AG/org.eclipse.scout.docs/blob";
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
      return "releases/9.0.x";
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
}
