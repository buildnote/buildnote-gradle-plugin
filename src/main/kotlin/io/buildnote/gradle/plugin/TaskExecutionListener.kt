@file:Suppress("DEPRECATION")

package io.buildnote.gradle.plugin

import io.buildnote.gradle.plugin.port.BuildStage
import io.buildnote.gradle.plugin.port.BuildStageCategory
import io.buildnote.gradle.plugin.port.DataEventStatus
import io.buildnote.gradle.plugin.port.asCompactJsonString
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.tasks.TaskState
import java.nio.file.Path
import java.util.*
import kotlin.io.path.appendText

fun taskExecutionListener(file: Path) = object : TaskExecutionListener {
    private var startTime: Long = 0

    override fun beforeExecute(task: Task) {
        startTime = System.currentTimeMillis()
    }

    override fun afterExecute(task: Task, state: TaskState) {
        val completedAt = System.currentTimeMillis()

        file.appendText(
            BuildStage(
                id = UUID.randomUUID(),
                timestamp = startTime,
                name = task.path,
                status = DataEventStatus.successful,
                startedAt = startTime,
                completedAt = completedAt,
                category = BuildStageCategory.command,
            ).asCompactJsonString() + "\n"
        )
    }
}