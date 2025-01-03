package io.buildnote.gradle.plugin.port

import io.buildnote.gradle.plugin.port.DataEventType.`build-stage`
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
    val completedAt: Long,
    val duration: Long,
    val category: BuildStageCategory,
) : DataEvent(`build-stage`)

@JsonSerializable
enum class BuildStageCategory {
    command
}

