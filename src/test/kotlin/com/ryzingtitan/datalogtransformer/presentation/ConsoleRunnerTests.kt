package com.ryzingtitan.datalogtransformer.presentation

import com.ryzingtitan.datalogtransformer.domain.transformer.services.services.RecordTransformationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ConsoleRunnerTests {
    @Nested
    inner class Run {
        @Test
        fun `starts the parsing process when a folder is provided`() {
            consoleRunner.run()

            verify(mockRecordTransformationService, times(1)).transform()
        }
    }

    @BeforeEach
    fun setup() {
        consoleRunner = ConsoleRunner(mockRecordTransformationService)
    }

    private lateinit var consoleRunner: ConsoleRunner

    private val mockRecordTransformationService = mock<RecordTransformationService>()
}
