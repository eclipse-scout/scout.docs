package org.eclipse.scout.contacts.ui.html;

import org.eclipse.scout.rt.platform.Replace;
import org.eclipse.scout.rt.server.commons.servlet.ContentSecurityPolicy;

@Replace
public class ContactsContentSecurityPolicy extends ContentSecurityPolicy {

  @Override
  protected void initDirectives() {
    super.initDirectives();
    // Contacts application seems to use gravatar images, see org.eclipse.scout.contacts.client.UserForm.getGravatarImage()
    withImgSrc("'self' www.gravatar.com");
  }
}
