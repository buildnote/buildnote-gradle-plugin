@file:Suppress("OVERRIDE_DEPRECATION")

package io.buildnote.gradle.plugin

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import java.io.File
import java.nio.file.Path
import kotlin.io.path.copyTo

fun buildListener(target: Project, file: Path) = object : BuildListener {
    private var startTime: Long = 0

    override fun settingsEvaluated(settings: Settings) {
    }

    override fun projectsLoaded(gradle: Gradle) {
        startTime = System.currentTimeMillis()
    }

    override fun projectsEvaluated(gradle: Gradle) {
    }

    override fun buildFinished(result: BuildResult) {
        val finalFile = File(
            target.layout.buildDirectory.dir("buildnote").get().asFile,
            "buildnote-gradle-events.ndjson"
        ).also { it.parentFile.mkdirs() }
        file.copyTo(finalFile.toPath(), true)
    }
}