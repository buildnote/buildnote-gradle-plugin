package io.buildnote.gradle.plugin.port

import io.buildnote.gradle.plugin.port.DataEventType.`build-stage`
import org.http4k.core.Uri
import se.ansman.kotshi.JsonSerializable
import se.ansman.kotshi.PolymorphicLabel
import java.util.*

@JsonSerializable
@PolymorphicLabel("build-stage")
data class BuildStage(
    override val id: UUID,
    override val timestamp: Long,
    val name: String,
    val status: DataEventStatus,
    val startedAt: Long,
    val completedAt: Long? = null,
    val url: Uri? = null,
    val duration: Long = 0,
    val category: BuildStageCategory = BuildStageCategory.phase,
    val relatedTo: String? = null,
    val message: String? = null,
) : DataEvent(`build-stage`)

@JsonSerializable
enum class BuildStageCategory {
    build, phase, step, command
}

