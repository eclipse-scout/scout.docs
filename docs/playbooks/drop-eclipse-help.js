'use strict'

module.exports.register = function () {
    this.once('contentClassified', ({ contentCatalog }) => {
        contentCatalog.getFiles().forEach((file) => {
            if (file.src.path && file.src.path.startsWith('modules/eclipse-help/')) {
                console.log('dropping file: ' + file.src.path);
                contentCatalog.removeFile(file);
            }
        })
    })
}

// file: {"stat":{"dev":2349777802,"mode":33206,"nlink":1,"uid":0,"gid":0,"rdev":0,"blksize":4096,"ino":6473924465479368,"size":1409,"blocks":8,"atimeMs":1679475392784.7607,"mtimeMs":1678894356519.1418,"ctimeMs":1678894356519.1418,"birthtimeMs":1677749295704.5117,"atime":"2023-03-22T08:56:32.785Z","mtime":"2023-03-15T15:32:36.519Z","ctime":"2023-03-15T15:32:36.519Z","birthtime":"2023-03-02T09:28:15.705Z"},"_contents":{"type":"Buffer","data":[…]},"history":["modules/common/examples/widgets/org.eclipse.scout.widgets/[jswidgets] all unix.launch"],"_cwd":"C:\\dev\\doc\\org.eclipse.scout.docs\\docs","_isVinyl":true,"_symlink":null,"src":{"abspath":"C:\\dev\\doc\\org.eclipse.scout.docs\\docs\\modules\\common\\examples\\widgets\\org.eclipse.scout.widgets\\[jswidgets] all unix.launch","path":"modules/common/examples/widgets/org.eclipse.scout.widgets/[jswidgets] all unix.launch","basename":"[jswidgets] all unix.launch","stem":"[jswidgets] all unix","extname":".launch","origin":{"type":"git","url":"https://github.com/eclipse-scout/scout.docs.git","gitdir":"C:\\dev\\doc\\org.eclipse.scout.docs\\.git","reftype":"branch","refname":"releases/22.0","branch":"releases/22.0","startPath":"docs","worktree":"C:\\dev\\doc\\org.eclipse.scout.docs","fileUriPattern":"file:///C:/dev/doc/org.eclipse.scout.docs/docs/%s","webUrl":"https://github.com/eclipse-scout/scout.docs","editUrlPattern":"https://github.com/eclipse-scout/scout.docs/edit/releases/22.0/docs/%s","descriptor":{"name":"scout-docs","title":"Eclipse Scout","version":"22","asciidoc":{"attributes":{"source-language":"asciidoc@","experimental":true,"sectanchors":true}},"nav":["modules/ROOT/nav.adoc","modules/getstarted/nav.adoc","modules/technical-guide/nav.adoc","modules/sdk/nav.adoc","modules/howtos/nav.adoc","modules/releasenotes/nav.adoc","modules/migration/nav.adoc"]}},"fileUri":"file:///C:/dev/doc/org.eclipse.scout.docs/docs/modules/common/examples/widgets/org.eclipse.scout.widgets/[jswidgets]%20all%20unix.launch","editUrl":"https://github.com/eclipse-scout/scout.docs/edit/releases/22.0/docs/modules/common/examples/widgets/org.eclipse.scout.widgets/[jswidgets]%20all%20unix.launch","family":"example","relative":"widgets/org.eclipse.scout.widgets/[jswidgets] all unix.launch","module":"common","moduleRootPath":"../../..","component":"scout-docs","version":"22"}}
