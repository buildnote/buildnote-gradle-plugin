package io.buildnote.gradle.plugin.port

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
enum class DataEventStatus {
    successful, failed, skipped, cancelled
}