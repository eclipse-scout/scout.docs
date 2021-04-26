#!/bin/bash

#############################################################################################
#
# Script copies built output zip from documents build into this git repo.
#  - Folder 'images' is always synchronized (checksum based)
#  - html files with their corresponding pdf and zip files:
#    - only copied there are changes in html except the "Last updated" date
#
#############################################################################################

# exit when any command fails
set -eE # same as: `set -o errexit -o errtrace`
# keep track of the last executed command
trap 'last_command=$current_command; current_command=$BASH_COMMAND' DEBUG
#trap 'last_command=$current_command; current_command=$BASH_COMMAND' DEBUG
# echo an error message before exiting
trap 'echo "\"${last_command}\" command failed with exit code $?."' ERR


if (( $# != 1 )); then
    echo "Expecting arguments: eclipsescout.github.io-output-version"
    echo "example: 6.1"
    exit 2
fi


# --- VARIABLES ---


outputVersion=$1
docsBuildDir="$(cd "$(dirname "$0")/.."; pwd)"
buildDir="target"
imagesDirName="images"
cssDirName="css"


# --- FUNCTIONS ---


publishHtmlImpl(){
  local srcHtmlFile=$1
  local docFilename=$2
  local htmlFile="${docFilename}/${docFilename}.html"

  # make a new (tmp) folders for html file and it's images
  mkdir -p "${docFilename}/${imagesDirName}"
  cp $srcHtmlFile $htmlFile

  echo "Publishing '${docFilename}.html' ..."

  grep -Eo 'src="[^"]*"' $htmlFile | while read -r srcHtmlAttribute ; do
    local imgPathRegex='^src="(.+\.(gif|png|jpg))"$'
    if [[ $srcHtmlAttribute =~ $imgPathRegex ]]; then
      local imgPath="${BASH_REMATCH[1]}"
      local imgFilename=${imgPath##*/}

      echo "Copy '$imgFilename' to '$imagesDirName/' and replace src attributes in HTML"

      # copy image to image folder
      cp "${imgPath}" "${docFilename}/${imagesDirName}/"

      # replace usages
      sed -r -i -e 's|src="'"${imgPath}"'"|src="'"${imagesDirName}/${imgFilename}"'"|g' $htmlFile
    fi
  done
}

publishHtml(){
  local srcModuleName=$1
  local docFilename=$2
  local sourceDir="${docsBuildDir}/${srcModuleName}/target/generated-docs/"

  publishHtmlImpl "${sourceDir}/${docFilename}.html" $docFilename

  # move files from temporary location to this dir
  mv -f ${docFilename}/${docFilename}.html ./
  mv -f ${docFilename}/${imagesDirName}/* ${imagesDirName}/ || echo "Ignoring empty images dir"
  rm -r $docFilename
}

publishDoc(){
  local srcModuleName=$1
  local docFilename=$2
  local subDir=$3
  local sourceDir="${docsBuildDir}/${srcModuleName}/target/generated-docs/"

  publishHtmlImpl "${sourceDir}/${docFilename}.html" $docFilename

  mkdir -p $subDir

  # copy pdf as-is
  echo "Copy '${docFilename}.pdf'"
  cp "${sourceDir}/${docFilename}.pdf" "${subDir}/${docFilename}.pdf"

  # create zip containing html and images
  echo "Create '${docFilename}.zip'"
  jar -cfM "${subDir}/${docFilename}.zip" $docFilename

  # move files from temporary location to subDir
  mv -f ${docFilename} "${subDir}/$docFilename"
}


# --- SCRIPT ---


# clean
rm -rf "${docsBuildDir}/publish/${buildDir}"


# ------------------------------------
# output for 'eclipse.org-scout'
# ------------------------------------

echo "Publish 'eclipse.org-scout'"

targetDir="${docsBuildDir}/publish/${buildDir}/published-docs/eclipse.org-scout"
mkdir -p $targetDir
cd $targetDir
mkdir $imagesDirName

publishHtml 'scout_install' 'scout_install'
publishHtml 'scout_helloworld' 'scout_helloworld'


# ------------------------------------
# output for 'eclipsescout.github.io'
# ------------------------------------

echo "Publish 'eclipsescout.github.io'"

targetDir="${docsBuildDir}/publish/${buildDir}/published-docs/eclipsescout.github.io/${outputVersion}"
mkdir -p $targetDir
cd $targetDir
mkdir $imagesDirName

publishDoc 'scout_install' 'scout_install' 'latest/article_install'
publishDoc 'scout_helloworld' 'scout_helloworld' 'latest/article_helloworld'
publishDoc 'book_scout_frontend' 'scout_frontend' 'latest/book_scout_frontend'
publishDoc 'book_scout_intro' 'scout_intro' 'latest/book_scout_intro'
publishDoc 'book_scout_architecture' 'scout_architecture' 'latest/book_scout_architecture'
publishDoc 'scout_migration' 'scout_migration_guide' 'latest/scout_migration'
mv 'latest/scout_migration/scout_migration_guide' 'latest/scout_migration/scout_migration'
mv 'latest/scout_migration/scout_migration_guide.zip' 'latest/scout_migration/scout_migration.zip'
publishDoc 'scout_releasenotes' 'scout_releasenotes' 'latest/scout_releasenotes'


# ------------------------------------
# output for 'scout-sdk-help'
# ------------------------------------

echo "Publish 'scout-sdk-help'"

targetDir="${docsBuildDir}/publish/${buildDir}/published-docs/scout-sdk-help/html"
mkdir -p $targetDir
cd $targetDir
mkdir $imagesDirName


# --- publish HTML files ---
for htmlSrcFile in ${docsBuildDir}/eclipse_help/target/generated-docs/*.html ; do
  htmlFilename=${htmlSrcFile##*/}
  docFilename=${htmlFilename%.*}

  publishHtml 'eclipse_help' $docFilename
done


# --- copy CSS replacements ---
echo "Copy CSS and replace href attributes in HTML (for 'scout-sdk-help/html/*.html')"
mkdir $cssDirName
cp "${docsBuildDir}/publish/css-replacement/eclipse.css" "${cssDirName}/eclipse.css"
cp "${docsBuildDir}/publish/css-replacement/coderay-eclipse.css" "${cssDirName}/coderay-eclipse.css"
# replace CSS file usages
sed -r -i -e 's|href="./asciidoctor.css"|href="'"${cssDirName}/eclipse.css"'"|g' *.html
sed -r -i -e 's|href="./coderay-asciidoctor.css"|href="'"${cssDirName}/coderay-eclipse.css"'"|g' *.html


# --- enrich HTML with navigation header and footer ---
echo "Enrich HTML with navigation header and footer (for 'scout-sdk-help/html/*.html')"
# copy nav images
cp "${docsBuildDir}/publish/nav_images/home.gif" "${imagesDirName}/home.gif"
cp "${docsBuildDir}/publish/nav_images/next.gif" "${imagesDirName}/next.gif"
cp "${docsBuildDir}/publish/nav_images/prev.gif" "${imagesDirName}/prev.gif"

# read help-pages.txt - readarray/mapfile command not available therefore array creation with cat command
helpPagesArray=($(cat ${docsBuildDir}/eclipse_help/help-pages.txt |tr "\n" " "))

# init titles array
declare -a helpPageTitlesArray
for i in "${!helpPagesArray[@]}" ; do
  htmlFile="${helpPagesArray[i]}"

  firstHeaderTag=$(grep -Eo -m 1 '>[^<>]+</h[[:digit:]]>' $htmlFile)
  tagContentRegex='>([^<>]+)</h[[:digit:]]>'
  if [[ $firstHeaderTag =~ $tagContentRegex ]]; then
    helpPageTitlesArray[i]=${BASH_REMATCH[1]}
  fi
done

homeHtmlFile="${helpPagesArray[0]}"
homeTitle="${helpPageTitlesArray[0]}"

prepareNavHtmlFragment(){
  # new lines escaped with an additional backslash '\' (required by sed command)
  navHtmlFragment='<table border="0" class="navigation" style="width: 100%;" summary="navigation">\
<tr>\
<td align="left" colspan="1" rowspan="1" style="width: 30%">'"${previousNavLink}"'</td>\
<td align="center" colspan="1" rowspan="1" style="width: 40%">'"${homeNavLink}"'</td>\
<td align="right" colspan="1" rowspan="1" style="width: 30%">'"${nextNavLink}"'</td>\
</tr>\
<tr>\
<td align="left" colspan="1" rowspan="1" style="width: 30%">'"${previousNavTitle}"'</td>\
<td align="center" colspan="1" rowspan="1" style="width: 40%"></td>\
<td align="right" colspan="1" rowspan="1" style="width: 30%">'"${nextNavTitle}"'</td>\
</tr>\
</table>'
}

# insert navigation into html file
for i in "${!helpPagesArray[@]}" ; do
  htmlFile="${helpPagesArray[i]}"
  echo "Adding navigation header and footer to '$htmlFile'"

  if [[ $i -gt 0 ]] ; then
    prevIndex=$((i - 1))
    previousNavHref="${helpPagesArray[prevIndex]}"
    previousNavTitle="${helpPageTitlesArray[prevIndex]}"
    previousNavLink='<a href="'"${previousNavHref}"'" shape="rect" title="'"${previousNavTitle}"'"><img alt="Previous" border="0" src="images/prev.gif"></a>'
  else
    previousNavHref=''
    previousNavTitle=''
    previousNavLink=''
  fi
  if [[ $i -lt $((${#helpPagesArray[@]} - 1)) ]] ; then
    nextIndex=$((i + 1))
    nextNavHref="${helpPagesArray[nextIndex]}"
    nextNavTitle="${helpPageTitlesArray[nextIndex]}"
    nextNavLink='<a href="'"${nextNavHref}"'" shape="rect" title="'"${nextNavTitle}"'"><img alt="Next" border="0" src="images/next.gif"></a>'
  else
    nextNavHref=''
    nextNavTitle=''
    nextNavLink=''
  fi

  # insert nav right after body tag
  homeNavLink=''
  prepareNavHtmlFragment
  sed -i -e 's|<body[^>]*>|\0\n'"${navHtmlFragment}"'|g' $htmlFile

  # insert nav right before footer (with home link)
  homeNavLink='<a href="'"${homeHtmlFile}"'" shape="rect" title="'"${homeTitle}"'"><img alt="'"${homeTitle}"'" border="0" src="images/home.gif"></a>'
  prepareNavHtmlFragment
  sed -i -e 's|<div id="footer"[^>]*>|'"${navHtmlFragment}"'\n\0|' $htmlFile

done



echo "Publish successfully completed"

