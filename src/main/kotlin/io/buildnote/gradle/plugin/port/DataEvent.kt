package io.buildnote.gradle.plugin.port
import io.buildnote.gradle.plugin.util.EventsJson
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.Polymorphic
import java.util.*

@JsonSerializable
@Polymorphic(labelKey = "type")
sealed class DataEvent(val type: DataEventType) {
    abstract val id: UUID
    abstract val timestamp: Long
}

fun DataEvent.asCompactJsonString() =
    EventsJson.compact(EventsJson.asJsonObject(this))
