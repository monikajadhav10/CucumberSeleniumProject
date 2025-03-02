package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import base.BasePage;
import java.util.*;

public class FooterPage extends BasePage {  

    @FindBy(xpath = "//footer[@data-testid='footer']")
    private WebElement footer;

    @FindBy(xpath = "//h2[contains(@class, 'text-base font-bold')]") // Find all headings (Team, Tickets etc)
    private List<WebElement> footerHeadings;

    public FooterPage() {
        super();
    }

    public void scrollToFooter() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footer);
    }

    // Get footer data (Heading → Links)
    public Map<String, List<String>> getFooterLinksBySection() {
        Map<String, List<String>> footerData = new LinkedHashMap<>();

        for (WebElement heading : footerHeadings) {
            String headingText = heading.getText().trim();
            String headingId = heading.getAttribute("id"); // Get ID for locating links

            if (!headingText.isEmpty() && headingId != null) {
                List<WebElement> sectionLinks = driver.findElements(By.xpath("//h2[@id='" + headingId + "']/following-sibling::ul//a"));
                List<String> links = new ArrayList<>();

                for (WebElement link : sectionLinks) {
                    String linkText = link.getText().trim();
                    if (!linkText.isEmpty()) {
                        links.add(linkText);
                    }
                }
                footerData.put(headingText, links);
            }
        }
        return footerData;
    }
}
