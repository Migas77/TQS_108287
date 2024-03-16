package com.tqs108287.App;


import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/tqs108287/App")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com/tqs108287/App")
public class CucumberTest {
}
