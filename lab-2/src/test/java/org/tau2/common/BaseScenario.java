package org.tau2.common;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.tau2.util.DriverBase;
import org.tau2.util.Sleeper;

import java.util.Date;

public class BaseScenario {

	private static final Logger log = LogManager.getLogger(BaseScenario.class);
	private final WebDriver driver = DriverBase.getWebDriver();

	@Given("Navigated to {string}")
	public void navigatedTo(String url) {
		log.info("Opening: " + url);
		driver.get(url);
		Sleeper.siesta(500L);
	}

	@Before
	public void setup(Scenario scenario) {
		log.info("STARTING NEW SCENARIO");
		scenario.log("Scenario - " + scenario.getName() + " - started at: " + new Date());
	}

	@After
	public void tearDown(Scenario scenario) {
		DriverBase.cleanupDriver();
		scenario.log("Scenario - " + scenario.getName() + " - ended at: " + new Date());
		log.info("/////////////////////////////////////////////////////////////////////////");
	}
}
