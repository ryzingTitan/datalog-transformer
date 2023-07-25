package com.ryzingtitan.datalogtransformer.presentation

import com.ryzingtitan.datalogtransformer.domain.transformer.services.services.RecordTransformationService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!cucumber")
class ConsoleRunner(private val recordTransformationService: RecordTransformationService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        recordTransformationService.transform()
    }
}
