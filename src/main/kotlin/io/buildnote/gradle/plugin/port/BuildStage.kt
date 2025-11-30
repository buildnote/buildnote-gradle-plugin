package io.buildnote.gradle.plugin.port

import io.buildnote.gradle.plugin.port.DataEventType.`build-stage`
import org.http4k.core.Uri
import se.ansman.kotshi.JsonSerializable
import java.util.*
import kotlin.to

fun BuildStage(
    id: UUID,
    timestamp: Long,
    status: DataEventStatus,
    name: String,
    category: BuildStageCategory,
    startedAt: Long,
    completedAt: Long,
    message: String? = null,
    url: Uri? = null
) = DataEvent(
    type = `build-stage`,
    id = id,
    timestamp = timestamp,
    status = status,
    content = mapOf(
        "event" to mapOf(
            "duration" to completedAt.let { it - startedAt }
        ),
        "buildStage" to listOfNotNull(
            "startedAt" to startedAt,
            "completedAt" to completedAt,
            "name" to name,
            "category" to category.name,
            message?.let { "message" to it },
            url?.let { "url" to it.toString() },
        ).toMap()
    )
)

@JsonSerializable
enum class BuildStageCategory {
    command
}

