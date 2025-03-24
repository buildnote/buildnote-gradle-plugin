@file:Suppress("UnstableApiUsage")

import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()
}

plugins {
    `kotlin-dsl`
    `java-test-fixtures`
    `jvm-test-suite`
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish") version "1.3.0"
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
}

group = "io.buildnote"
version = "0.0.5"

gradlePlugin {
    website.set("https://buildnote.io")
    vcsUrl.set("https://github.com/buildnote/buildnote-gradle-plugin")
    plugins {
        create("BuildnoteGradle") {
            id = "io.buildnote.buildnote-gradle-plugin"
            implementationClass = "io.buildnote.gradle.plugin.BuildnoteGradlePlugin"
            displayName = "Buildnote Gradle Plugin"
            description = "Buildnote gradle plug-in"
            tags = listOf("buildnote", "build", "analytics")
        }
    }

}

kotlin {
    jvmToolchain(21)
}

sourceSets {
    main {
        kotlin {
            srcDirs += File("build/generated/ksp/main/kotlin")
        }
    }
    test {
        kotlin {
            srcDirs += File("build/generated/ksp/test/kotlin")
        }
    }
}

tasks {
    withType<KotlinJvmCompile> {
        compilerOptions {
            jvmTarget = JVM_21
            allWarningsAsErrors = true
        }
    }

    val packageSources by register("sourcesJar", Jar::class) {
        archiveClassifier.set("sources")
        from(project.the<SourceSetContainer>()["main"].allSource)
    }

    val packageJavadoc by register("javadocJar", Jar::class) {
        archiveClassifier.set("javadoc")
        from(named<Javadoc>("javadoc").get().destinationDir)
        dependsOn(named("javadoc"))
    }

    artifacts {
        archives(packageJavadoc)
        archives(packageSources)
    }

    java {
        sourceCompatibility = VERSION_21
        targetCompatibility = VERSION_21
    }

    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes["buildnote_gradle_plugin_version"] = archiveVersion
        }
    }

    // https://docs.gradle.org/current/userguide/working_with_files.html#sec:reproducible_archives
    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    publishing {
        repositories {
            maven {
                name = "StagingDeploy"
                setUrl(project.layout.buildDirectory.dir("staging-deploy"))
            }
        }

        publications {
            create<MavenPublication>("buildnote") {
                artifactId = project.name

                pom {
                    name.set(project.name)
                    description.set("Gradle plugin that records task times for buildnote collection")
                    url.set("https://github.com/buildnote/buildnote-gradle-plugin")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            name.set("Buildnote Limited")
                            email.set("team@buildnote.io")
                        }
                    }

                    scm {
                        connection.set("scm:git:git@github.com:buildnote/buildnote-gradle-plugin.git")
                        developerConnection.set("scm:git:git@github.com:buildnote/buildnote-gradle-plugin.git")
                        url.set("git@github.com:buildnote/buildnote-gradle-plugin.git")
                    }
                }

                artifact(packageSources)
                artifact(packageJavadoc)
            }
        }
    }
}

dependencies {
    ksp("se.ansman.kotshi:compiler:3.0.0")
    ksp("org.http4k:http4k-connect-ksp-generator:6.2.0.0")

    api(platform("org.http4k:http4k-bom:6.0.0.0"))
    api(platform("dev.forkhandles:forkhandles-bom:2.20.0.0"))
    api("dev.forkhandles:values4k")
    api("dev.forkhandles:result4k")
    api("org.http4k:http4k-core")
    api("org.http4k:http4k-config")

    api("se.ansman.kotshi:api:3.0.0")
    api("org.http4k:http4k-format-moshi")


    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation(platform("org.http4k:http4k-bom:6.0.0.0"))
    testImplementation(platform("dev.forkhandles:forkhandles-bom:2.20.0.0"))

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-testing-strikt")
    testImplementation("dev.forkhandles:result4k-strikt")
}