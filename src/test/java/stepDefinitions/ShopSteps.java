package stepDefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.ShopPage;

public class ShopSteps {
    private ShopPage shopPage;

    public ShopSteps() {
        this.shopPage = new ShopPage();
    }

    @When("User hovers over Shop and clicks on Men's")
    public void userHoversOverShopAndClicksOnMens() {
        shopPage.hoverOverShopAndClickMens();
    }

    @Then("User extracts and saves jacket details and save in the report")
    public void userExtractsAndSavesJacketDetailsAndSaveInTheReport() {
        shopPage.extractJacketDetails();
    }
    
    @Then("User selects jackets radio button")
    public void UserSelectsJacketsRadioButton() {
        shopPage.clickOnJacketsRadioButton();
    }
}
