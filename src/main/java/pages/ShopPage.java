package pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class ShopPage extends BasePage {

	private static final String FOLDER_PATH = "/Users/monika/eclipse-workspace/NewSeleniumProject/target/";

	@FindBy(xpath = "//a[@class='accent-primary-border _link_ob2qz_1 text-2sm']/span[text()='Shop']")
	private WebElement shopMenu;

	@FindBy(xpath = "//ul[contains(@class,'list_nmaf4_42')]//a[contains(text(),\"Men's\")]")
	private WebElement mensSection;

//	@FindBy(xpath = "//div[contains(@class, 'product-card-title')]/a")
//	private List<WebElement> jacketTitles;
//
//	@FindBy(xpath = "//span[contains(@class, 'money-value')]")
//	private List<WebElement> jacketPrices;

	@FindBy(xpath = "//div[@class='side-nav-facet-items allDepartmentsBoxes']//a[span[text()='Jackets']]")
	private WebElement jacketRadioButton;

	@FindBy(xpath = "//li[contains(@class, 'next-page')]/a")
	private List<WebElement> nextPageButtons;

	@FindBy(xpath = "//li[contains(@class, 'next-page') and contains(@class, 'disabled')]")
	private List<WebElement> nextPageDisabled;
//	
//	@FindBy(xpath = "//div[contains(@class,'product-card')]//span[contains(@class,'price primary')]//span[contains(@class,'money-value')]")
//	private List<WebElement> salePrices;
//	
//	@FindBy(xpath = "//div[contains(@class,'product-card')]//span[contains(@class,'money-value')]")
//	private List<WebElement> regularPrices;

	@FindBy(css = ".grid.grid-small-2-medium-3.row.small-up-2.medium-up-3 .column")
	private List<WebElement> jacketContainers;

	@FindBy(css = ".product-card-title")
	private List<WebElement> jacketTitles;

	@FindBy(css = ".price.primary") // Parent of sale price
	private List<WebElement> salePriceContainers;

	@FindBy(css = ".price.primary .money-value") // Sale prices
	private List<WebElement> salePrices;

	@FindBy(css = ".money-value") // All prices (including regular and sale)
	private List<WebElement> allPrices;
	
	@FindBy(xpath = "//span[contains(@class, 'money-value')]//span[contains(@class, 'sr-only')]")
	private List<WebElement> priceElements;

//	@FindBy(css = ".next-page-button")
//	private List<WebElement> nextPageButtons;
//
//	@FindBy(css = ".next-page-button.disabled")
//	private List<WebElement> nextPageDisabled;

	// Default constructor using WebDriver from BasePage
	public ShopPage() {
		super(); // Calls BasePage constructor, which initializes WebDriver
	}

	public void hoverOverShopAndClickMens() {
		Actions actions = new Actions(driver);
		actions.moveToElement(shopMenu).perform();
		mensSection.click();

		// Handle new tab
		String currentWindow = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (!window.equals(currentWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}
	}

	public void extractJacketDetails() {
		File directory = new File(FOLDER_PATH);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(FOLDER_PATH + "jacket_details.txt");

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			do {
				wait.until(ExpectedConditions.visibilityOfAllElements(jacketContainers));

				for (int i = 0; i < jacketContainers.size(); i++) {
					try {
						WebElement jacket = jacketContainers.get(i);

						// Get title
						String title = (i < jacketTitles.size()) ? jacketTitles.get(i).getText().trim() : "No Title";

						// Extract price
						String price = extractPrice(jacket);

						// Write to file
						writer.write("Jacket: " + title + " | Price: " + price.trim());
						writer.newLine();
						System.out.println("Jacket: " + title + " | Price: " + price);

					} catch (Exception e) {
						System.out.println("Skipping an item due to an error: " + e.getMessage());
						continue;
					}
				}

				// Check if the "Next Page" button is disabled
				if (!nextPageDisabled.isEmpty()) {
					System.out.println("Reached last page. No more pages to navigate.");
					break;
				}
				// Click the last "Next Page" button
				if (!nextPageButtons.isEmpty()) {
					WebElement nextPage = nextPageButtons.get(nextPageButtons.size() - 1);
					wait.until(ExpectedConditions.elementToBeClickable(nextPage)).click();

					// Wait for the next page to load
					wait.until(ExpectedConditions.visibilityOfAllElements(jacketTitles));
				} else {
					System.out.println("No 'Next Page' button found, stopping pagination.");
					break;
				}

			} while (true);
			System.out.println("File saved at: " + file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Error writing to file: " + e.getMessage());
		}
	}

	private String extractPrice(WebElement jacket) {
		String price = "No Price";
		String salePrice = "";
		String regularPrice = "";

		try {
			// Extract price using .sr-only (cleanest value)
			List<WebElement> priceElements = jacket.findElements(By.cssSelector(".money-value .sr-only"));
			if (!priceElements.isEmpty()) {
				return priceElements.get(0).getText().trim();
			}

			// Case 1: Sale price & strike-through price exist
			if (jacket.findElements(By.cssSelector(".price.primary .money-value")).size() > 0
					&& jacket.findElements(By.cssSelector(".strike-through .money-value")).size() > 0) {

				salePrice = jacket.findElement(By.cssSelector(".price.primary .money-value")).getText().trim();
				regularPrice = jacket.findElement(By.cssSelector(".strike-through .money-value")).getText().trim();
				price = "Sale: " + salePrice + " (Regular: " + regularPrice + ")";
			}
			// Case 2: Multiple prices
			else if (jacket.findElements(By.cssSelector(".money-value")).size() > 1) {
				List<WebElement> multiplePrices = jacket.findElements(By.cssSelector(".money-value"));
				price = multiplePrices.get(0).getText().trim() + " - " + multiplePrices.get(1).getText().trim();
			}
			// Case 3: Only one price available
			else if (jacket.findElements(By.cssSelector(".money-value")).size() == 1) {
				price = jacket.findElement(By.cssSelector(".money-value")).getText().trim();
			}

		} catch (Exception e) {
			System.out.println("Error extracting price: " + e.getMessage());
		}

		return price;
	}

	public void clickOnJacketsRadioButton() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

		try {
			// Wait for the pop-up to disappear if it exists (up to 8 seconds)
			try {
				wait.withTimeout(Duration.ofSeconds(8))
						.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".countdown-content")));
				System.out.println("Pop-up disappeared.");
			} catch (Exception e) {
				System.out.println("Pop-up not found or already gone.");
			}

			// Wait for the Jackets radio button to be present
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[span[text()='Jackets']]")));

			// Scroll into view and ensure visibility
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({block: 'center', inline: 'center'});", jacketRadioButton);
			Thread.sleep(500); // Allow the scroll to complete

			// Wait for the element to be clickable
			wait.until(ExpectedConditions.elementToBeClickable(jacketRadioButton));

			// Click the Jackets radio button
			try {
				jacketRadioButton.click();
			} catch (Exception e) {
				System.out.println("Click intercepted. Trying JavaScript click...");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", jacketRadioButton);
			}

			System.out.println("Clicked on the Jackets radio button.");

		} catch (TimeoutException e) {
			System.out.println("Timed out waiting for the pop-up to disappear. Proceeding anyway...");
		} catch (Exception e) {
			System.out.println("Failed to click on Jackets radio button: " + e.getMessage());
		}
	}

}
