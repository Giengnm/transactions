package net.bank.transactions.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("cucumber-features")
@ConfigurationParameter(key = "cucumber.glue", value = "net.bank.transactions.cucumber")
public class CucumberIT {

}
