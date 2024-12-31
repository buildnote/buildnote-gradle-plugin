package io.buildnote.gradle.plugin.port
import org.http4k.format.Moshi.asJsonObject
import org.http4k.format.Moshi.compact
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.Polymorphic
import java.util.UUID

@JsonSerializable
@Polymorphic(labelKey = "type")
sealed class DataEvent(val type: DataEventType) {
    abstract val id: UUID
    abstract val timestamp: Long
}

fun DataEvent.asCompactJsonString() =
    compact(asJsonObject(this))
