#!/bin/sh

#
# Copyright (c) 2010, 2023 BSI Business Systems Integration AG
#
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which is available at https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#

# Abort the script if any command fails
set -e

# Specify the path to the node and npm binaries
PATH=$PATH:/usr/local/bin

# Check if node is available
command -v node >/dev/null 2>&1 || { echo >&2 "npm cannot be found. Make sure Node.js is installed and the PATH variable correctly set. See the content of this script for details."; exit 1; }

# Check if pnpm is available
command -v pnpm >/dev/null 2>&1 || { echo >&2 "pnpm cannot be found. Make sure pnpm is installed. See the content of this script for details."; exit 1; }

# Install all JavaScript dependencies defined in the package.json => creates the node_modules folder
cd ../..
echo Running 'pnpm install' in "$(pwd)"
pnpm install --ignore-scripts || exit $?
echo pnpm install finished successfully!
echo

# Build the JavaScript and CSS bundles and start the watcher => creates the dist folder
cd widgets/org.eclipse.scout.widgets.ui.html.app || exit 1
echo Running 'npm build:dev:watch' in "$(pwd)"
npm run build:dev:watch || exit $?
