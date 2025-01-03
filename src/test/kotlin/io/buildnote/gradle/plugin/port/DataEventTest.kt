package io.buildnote.gradle.plugin.port

import io.buildnote.gradle.plugin.BuildStageFixtures
import org.http4k.core.ContentType.Companion.APPLICATION_JSON
import org.http4k.testing.ApprovalTest
import org.http4k.testing.Approver
import org.http4k.testing.assertApproved
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ApprovalTest::class)
class DataEventTest {

    @Test
    fun `correctly formats build stage event`(approver: Approver) {
        approver.assertApproved(BuildStageFixtures.buildStageExample.asCompactJsonString(), APPLICATION_JSON)
    }

}