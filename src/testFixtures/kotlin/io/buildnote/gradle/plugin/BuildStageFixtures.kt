package io.buildnote.gradle.plugin

import io.buildnote.gradle.plugin.port.BuildStage
import io.buildnote.gradle.plugin.port.BuildStageCategory
import io.buildnote.gradle.plugin.port.DataEventStatus
import java.util.*

object BuildStageFixtures {
    val buildStageExample = BuildStage(
        id = UUID(0, 0),
        timestamp = 0,
        startedAt = 0,
        completedAt = 10,
        duration = 10,
        name = "stage-1",
        status = DataEventStatus.successful,
        category = BuildStageCategory.command
    )
}