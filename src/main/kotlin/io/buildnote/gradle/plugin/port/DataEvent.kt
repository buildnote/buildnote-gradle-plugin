package io.buildnote.gradle.plugin.port

import io.buildnote.gradle.plugin.util.EventsJson
import se.ansman.kotshi.JsonSerializable
import java.util.*

@JsonSerializable
data class DataEvent(
    val type: DataEventType,
    val id: UUID,
    val timestamp: Long,
    val status: DataEventStatus = DataEventStatus.successful,
    val content: Map<String, Any> = emptyMap(),
)

fun DataEvent.asCompactJsonString() =
    EventsJson.compact(EventsJson.asJsonObject(this))
