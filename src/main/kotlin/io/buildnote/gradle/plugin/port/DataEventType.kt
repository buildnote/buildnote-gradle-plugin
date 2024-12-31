package io.buildnote.gradle.plugin.port

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
enum class DataEventType {
    `build-stage`,
}