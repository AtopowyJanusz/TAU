package org.tau2.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class DriverBase {

	private static final Logger log = LogManager.getLogger(DriverBase.class);
	private static WebDriver driver;

	private static WebDriver initializeWebDriver() {
		log.info("Initializing web driver...");
		String webDriverStr = System.getProperty("browser", "chrome");
		WebDriver driver;
		switch(webDriverStr) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			case "opera":
				driver = new OperaDriver();
				break;
			default:
				throw new RuntimeException("No such driver: " + webDriverStr);
		}
		log.info("Web driver initialized: " + webDriverStr);
		return driver;
	}

	public static WebDriver getWebDriver() {
		log.info("Getting web driver...");
		if (driver == null) {
			driver = initializeWebDriver();
		}
		return driver;
	}

	public static void cleanupDriver() {
		log.info("Closing web driver...");
		driver.quit();
		driver = null;
		log.info("Web driver closed.");
	}



}
