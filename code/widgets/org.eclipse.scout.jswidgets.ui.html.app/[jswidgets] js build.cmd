@echo off

:: This script installs all the JS dependencies and builds the JavaScript and CSS bundles.
:: It also starts a watcher which triggers a rebuild of these bundles whenever JS or CSS code changes.
::
:: It has to be run once before the UI server is started.
:: You need to rerun it, if you update your JS dependencies (package.json).
:: Please see the Scout documentation for details about the available run scripts: https://eclipsescout.github.io/10.0/technical-guide-js.html#command-line-interface-cli
::
::Â To make this script work you need a current version of Node.js (>=12.1.0) and npm (>=6.9.0).
::Â Node.js (incl. npm) is available here: https://nodejs.org/.

::Â Check if npm is available
where npm >nul 2>nul
if %errorlevel% neq 0 (
  echo npm cannot be found. Make sure Node.js is installed and the PATH variable correctly set. See the content of this script for details. 1>&2
  exit /b 1
)

:: Install pnpm
echo Installing 'pnpm' into ../../node_modules
call npm install pnpm@">=5.0.0 <6.0.0" --prefix ../../node_modules
echo.

:: Install all JavaScript dependencies defined in the package.json => creates the node_modules folder
call cd ..\..
echo Running 'pnpm install' in %cd%
call node_modules\pnpm install --ignore-scripts
if %errorlevel% neq 0 exit /b %errorlevel%
echo pnpm install finished successfully!
echo.

:: Build the JavaScript and CSS bundles and start the watcher => creates the dist folder
cd widgets/org.eclipse.scout.jswidgets.ui.html.app
echo Running 'npm build:dev:watch' in %cd%
call npm run build:dev:watch
if %errorlevel% neq 0 exit /b %errorlevel%
