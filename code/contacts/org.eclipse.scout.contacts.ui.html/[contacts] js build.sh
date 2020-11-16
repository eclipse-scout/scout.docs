#! /bin/sh

# This script installs all the JS dependencies and builds the JavaScript and CSS bundles.
# It also starts a watcher which triggers a rebuild of these bundles whenever JS or CSS code changes.
#
# It has to be run once before the UI server is started.
# You need to rerun it, if you update your JS dependencies (package.json).
# Please see the Scout documentation for details about the available run scripts: https://eclipsescout.github.io/10.0/technical-guide-js.html#command-line-interface-cli
#
# To make this script work you need a current version of Node.js (>=12.1.0) and npm (>=6.9.0).
# Node.js (incl. npm) is available here: https://nodejs.org/.

# Check if npm is available
command -v npm >/dev/null 2>&1 || { echo >&2 "npm cannot be found. Make sure Node.js is installed and the PATH variable correctly set. See the content of this script for details. Aborting."; exit 1; }

# Install pnpm
echo Installing 'pnpm' into ../../node_modules
npm install pnpm@">=5.0.0 <6.0.0" --prefix "../../"
echo

# Install all JavaScript dependencies defined in the package.json => creates the node_modules folder
cd ../..
echo Running 'pnpm install' in "$(pwd)"
./node_modules/.bin/pnpm install --ignore-scripts || exit $?
echo pnpm install finished successfully!
echo

# Build the JavaScript and CSS bundles and start the watcher => creates the dist folder
cd contacts/org.eclipse.scout.contacts.ui.html || exit 1
echo Running 'npm build:dev:watch' in "$(pwd)"
npm run build:dev:watch || exit $?
