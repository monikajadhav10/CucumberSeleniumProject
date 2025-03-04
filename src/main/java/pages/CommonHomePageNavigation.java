package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

import java.time.Duration;

public class CommonHomePageNavigation extends BasePage {
	// Locators for pop-ups
	
	@FindBy(xpath = "//div[@class='p-2 absolute right-3 hover:cursor-pointer']")
	private WebElement ticketPopup;

	@FindBy(xpath = "//*[@id='onetrust-reject-all-handler']")
	private WebElement declinePopup;

    public void userIsOnTeamHomePage(String teamUrl) {
        openUrl(teamUrl); // method is in base page

       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        boolean ticketPopupHandled = false;
        boolean declinePopupHandled = false;
        boolean popUpAppeared = false;

        for (int i = 0; i < 2; i++) { // Loop twice to check both pop-ups
            try {
                wait.until(ExpectedConditions.visibilityOf(ticketPopup));
                wait.until(ExpectedConditions.elementToBeClickable(ticketPopup));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ticketPopup);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ticketPopup);
                System.out.println("Closed Ticket Access pop-up.");
                ticketPopupHandled = true;
                popUpAppeared = true;
            } catch (Exception e) {
                System.out.println("Ticket Access pop-up not found.");
            }

            try {
                wait.until(ExpectedConditions.visibilityOf(declinePopup));
                wait.until(ExpectedConditions.elementToBeClickable(declinePopup));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", declinePopup);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", declinePopup);
                System.out.println("Closed 'I Decline' pop-up.");
                declinePopupHandled = true;
                popUpAppeared = true;
            } catch (Exception e) {
                System.out.println("'I Decline' pop-up not found.");
            }

            if (!popUpAppeared) {
                break;
            }

            if (ticketPopupHandled && declinePopupHandled) {
                break;
            }
        }
    }
}
