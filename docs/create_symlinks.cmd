rmdir modules\common\examples /q /s
mkdir modules\common\examples\contacts
mklink /d modules\common\examples\org.eclipse.scout.docs.snippets ..\..\..\..\code\org.eclipse.scout.docs.snippets
mklink /d modules\common\examples\contacts\org.eclipse.scout.contacts.client ..\..\..\..\..\code\contacts\org.eclipse.scout.contacts.client
mklink /d modules\common\examples\contacts\org.eclipse.scout.contacts.shared ..\..\..\..\..\code\contacts\org.eclipse.scout.contacts.shared
mklink /d modules\common\examples\contacts\org.eclipse.scout.contacts.server ..\..\..\..\..\code\contacts\org.eclipse.scout.contacts.server
mklink /d modules\common\examples\contacts\org.eclipse.scout.contacts.server.app.dev ..\..\..\..\..\code\contacts\org.eclipse.scout.contacts.server.app.dev
