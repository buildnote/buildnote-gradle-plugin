package io.buildnote.gradle.plugin.port

import io.buildnote.gradle.plugin.port.DataEventType.`build-stage`
import org.http4k.core.Uri
import se.ansman.kotshi.JsonSerializable
import java.util.*

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
    attributes = listOfNotNull(
        "event.duration" to (completedAt.let { it - startedAt }).toString(),
        "buildStage.startedAt" to startedAt.toString(),
        "buildStage.completedAt" to completedAt.toString(),
        "buildStage.name" to name,
        "buildStage.category" to category.name,
        message?.let { "buildStage.message" to it },
        url?.let { "buildStage.url" to it.toString() },
    ).toMap()
)

@JsonSerializable
enum class BuildStageCategory {
    command
}

