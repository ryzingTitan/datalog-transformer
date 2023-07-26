package com.ryzingtitan.datalogtransformer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class DatalogTransformerApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<DatalogTransformerApplication>(*args)
}
