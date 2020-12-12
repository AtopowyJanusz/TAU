package org.tau2.website.github;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.tau2.util.DriverBase;
import org.tau2.util.Sleeper;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

	public static final Logger log = LogManager.getLogger(LoginSteps.class);
	private final WebDriver driver = DriverBase.getWebDriver();

	@When("Enter login and password {string} {string}")
	public void enterLoginAndPassword(String login, String password) {
		log.info("Enter login: " + login);
		driver.findElement(By.id("login_field")).sendKeys(login);
		Sleeper.siesta(500L);
		log.info("Enter password: " + password);
		driver.findElement(By.id("password")).sendKeys(password);
		Sleeper.siesta(500L);
	}

	@And("Click login button")
	public void clickLoginButton() {
		log.info("Clicking login");
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();
		Sleeper.siesta(500L);
	}

	@Then("Error flash exists with message {string}")
	public void errorFlashExistsWithMessage(String message) {
		log.info("Verifying error");
		WebElement element = driver.findElement(By.className("flash-error"));
		assertNotNull(element);
		assertEquals(message, element.getText());
		Sleeper.siesta(500L);
	}

	@Then("{string} link exists")
	public void linkExists(String text) {
		log.info("Checking if: " + text + " exists");
		WebElement element = driver.findElement(By.xpath("//a[text()='" + text + "']"));
		assertNotNull(element);
		Sleeper.siesta(500L);
	}

	@When("Navigated to {string} link")
	public void navigatedToPage(String link) {
		log.info("Navigating to link: " + link);
		WebElement element = driver.findElement(By.xpath("//a[text()='" + link + "']"));
		driver.get(element.getAttribute("href"));
		Sleeper.siesta(500L);
	}

	@Then("Header{int} {string} exists")
	public void headerExists(int headerLevel, String text) {
		log.info("Checking if header text: " + text + " exists");
		String header = "h" + headerLevel;
		WebElement element = driver.findElement(By.tagName(header));
		assertNotNull(element);
	}
}
