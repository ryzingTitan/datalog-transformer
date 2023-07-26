package com.ryzingtitan.datalogtransformer.cucumber

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@CucumberContextConfiguration
@ActiveProfiles("cucumber")
@SpringBootTest(
    classes = [com.ryzingtitan.datalogtransformer.DatalogTransformerApplication::class],
)
class CucumberContextConfiguration
