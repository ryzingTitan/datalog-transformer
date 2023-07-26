package com.ryzingtitan.datalogtransformer.cucumber

import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

@Suite
@SelectClasspathResource("features")
class CucumberTestRunner
