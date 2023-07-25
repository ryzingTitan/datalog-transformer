package com.ryzingtitan.datalogtransformer.cucumber

import com.ryzingtitan.datalogtransformer.domain.transformer.services.services.RecordTransformationService
import io.cucumber.java.en.When

class ConsoleRunnerStepDefs(private val recordTransformationService: RecordTransformationService) {
    @When("the datalogs are transformed")
    fun whenTheDatalogsAreTransformed() {
        recordTransformationService.transform()
    }
}
