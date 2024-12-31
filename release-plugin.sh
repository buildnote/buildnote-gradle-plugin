#!/bin/bash
set -e

PROJECT_ENV_FILE="$HOME/dev/buildnote/buildnote.env"

source "$PROJECT_ENV_FILE"

./gradlew :buildnote-gradle-plugin:build
./gradlew :buildnote-gradle-plugin:shadowJar
./gradlew :buildnote-gradle-plugin:publishPlugins
