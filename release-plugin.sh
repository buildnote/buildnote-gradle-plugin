#!/bin/bash
set -e

PROJECT_ENV_FILE="$HOME/dev/buildnote/buildnote.env"

source "$PROJECT_ENV_FILE"

./gradlew build publishPlugins
