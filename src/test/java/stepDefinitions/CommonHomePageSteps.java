package stepDefinitions;

import io.cucumber.java.en.Given;
import pages.CommonHomePageNavigation;
import utils.TeamUrlProvider;

public class CommonHomePageSteps {

    private final CommonHomePageNavigation commonHomePageNavigation;

    public CommonHomePageSteps() {
        this.commonHomePageNavigation = new CommonHomePageNavigation();
    }

    @Given("User is on the {string} home page")
    public void userIsOnTheHomePage(String team) {
        String url = TeamUrlProvider.getTeamUrl(team);
        commonHomePageNavigation.userIsOnTeamHomePage(url);
    }
}
