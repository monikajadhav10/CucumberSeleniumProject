package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import utils.WebDriverManager;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = WebDriverManager.getDriver(); 
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        PageFactory.initElements(driver, this); 
    }

    public void openUrl(String url) {
        try {
            driver.get(url);
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("Error opening URL: " + url);
            e.printStackTrace();
        }
    }
}
