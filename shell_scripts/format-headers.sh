#!/bin/bash
# Set correct copyright headers

mvn license:format generate-resources -f code $*
