package org.tau2.website.morele;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.tau2.common.BaseScenario;
import org.tau2.util.DriverBase;
import org.tau2.util.Sleeper;


import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PriceAlarmSteps {

	private static final Logger log = LogManager.getLogger(BaseScenario.class);
	private final WebDriver driver = DriverBase.getWebDriver();
	private String linkToRandomProduct;
	private String oldAlarmPagePrice;

	@Then("All cards have price badge")
	public void allCardsHavePriceBadge() {
		log.info("Verifying if all elements contains price badges");
		int productElementsCount = driver.findElements(By.className("productItemData")).size();
		int badgeElementsCount = driver.findElements(By.className("price-badge")).size();
		log.info(productElementsCount + " products and " + badgeElementsCount + " discounts");
		assertEquals(productElementsCount, badgeElementsCount);
		Sleeper.siesta(500L);
	}

	@When("Pick random product link and old price")
	public void pickRandomProductLinkAndOldPrice() {
		log.info("Getting product link and old price");
		Sleeper.siesta(2000L);
		List<WebElement> products = driver.findElements(By.className("productItemData"));
		Random random = new Random();
		WebElement randomProduct = products.get(random.nextInt(10) + 1);
		linkToRandomProduct = randomProduct.findElement(By.className("product-link")).getAttribute("href");
		WebElement oldPriceElement = randomProduct.findElement(By.className("price-old"));
		WebDriverWait wait = new WebDriverWait(driver, 25);
		wait.until((ExpectedCondition<Boolean>) webDriver -> oldPriceElement.getText().trim().length() != 0);
		oldAlarmPagePrice = oldPriceElement.getText()
				.replace(" ", "")
				.replace(",00", "")
				.replace("zł", "");
		Sleeper.siesta(500L);
	}

	@And("Navigate to selected product")
	public void navigateToSelectedProduct() {
		log.info("Navigating to selected random product: " + linkToRandomProduct);
		driver.get(linkToRandomProduct);
		Sleeper.siesta(500L);
	}

	@Then("Old price on alarm page matches brutto price on product page")
	public void oldPriceOnAlarmPageMatchesBruttoPriceOnProductPage() {
		log.info("Verifying if old price on alarm page matches brutto price on product page");
		String bruttoPrice = driver.findElement(By.id("product_price_brutto")).getAttribute("data-default")
				.replace(" ", "")
				.replace("zł", "");
		log.info("Old alarm price: " + oldAlarmPagePrice);
		log.info("Brutto page price before discount code: " + bruttoPrice);
		assertEquals(oldAlarmPagePrice, bruttoPrice);
	}
}
