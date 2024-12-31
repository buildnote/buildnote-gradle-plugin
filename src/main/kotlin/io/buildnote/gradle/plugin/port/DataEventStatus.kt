package io.buildnote.gradle.plugin.port

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
enum class DataEventStatus {
    running, successful, failed, skipped, cancelled
}