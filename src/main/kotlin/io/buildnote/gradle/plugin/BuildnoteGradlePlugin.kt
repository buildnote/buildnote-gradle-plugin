@file:Suppress("UnstableApiUsage", "DEPRECATION", "OVERRIDE_DEPRECATION")

package io.buildnote.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Files

abstract class BuildnoteGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val file = Files.createTempFile("buildnote-gradle-plugin", ".ndjson")
        with(target.gradle) {
            addListener(taskExecutionListener(file))
            addListener(buildListener(target, file))
        }
    }
}

