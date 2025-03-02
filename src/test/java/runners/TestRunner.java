/*package runners;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utils.WebDriverManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Parameters({"cucumber.options"})
    @BeforeClass
    public void setUp(@Optional("") String cucumberOptions) {
        System.setProperty("cucumber.options", cucumberOptions);
        WebDriverManager.getDriver();
        System.out.println("WebDriver initialized with tags: " + cucumberOptions);
    }

//    @Override
//    @DataProvider(parallel = true)
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }

    @AfterClass
    public static void closeDriver() {
        WebDriverManager.quitDriver();
        System.out.println("WebDriver closed.");
    }
}*/
