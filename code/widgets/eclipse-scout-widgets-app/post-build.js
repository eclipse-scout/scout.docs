/*******************************************************************************
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
const DIST_DIR = './dist/';
const LIST_FILE = 'file-list';
const DEV_MODE = process.argv[2] !== 'production';
const OUT_DIR = DIST_DIR + (DEV_MODE ? 'dev/' : 'prod/');

const fs = require('fs');
const errno = require('errno');
const path = require('path');

fs.readdirSync(OUT_DIR)
  .filter(f => /.*theme.*\.js/.test(f))
  .forEach(f => deleteFile(OUT_DIR + f))

if (!DEV_MODE) {
  createFileList();
}

function deleteFile(filename) {
  fs.unlink(filename, (err) => {
    if (err) {
      if (err.errorno === errno.ENOENT) {
        console.log('file does not exist', filename);
        return;
      } else {
        throw err;
      }
    }
    console.log('deleted ', filename);
  });
}

function createFileList() {
  let content = '';
  fs.readdirSync(OUT_DIR, {withFileTypes: true})
    .filter(dirent => dirent.isFile() && dirent.name !== LIST_FILE)
    .forEach(dirent => content += dirent.name + '\n');
  fs.writeFileSync(path.join(OUT_DIR, LIST_FILE), content, {flag: 'w'});
  console.log('# created file-list:\n' + content);
}
