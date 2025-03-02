package hooks;

import io.cucumber.java.After;
import utils.WebDriverManager;

public class CloseBrowser {

    @After
    public void closeBrowers() {
        System.out.println("Closing WebDriver");
        WebDriverManager.quitDriver();
    }
}
