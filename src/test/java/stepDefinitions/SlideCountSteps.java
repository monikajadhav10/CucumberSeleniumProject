package stepDefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.SlidePage;
import java.io.IOException;

public class SlideCountSteps {

    private SlidePage slidePage = new SlidePage(); 
    private int slideCount;

    @When("User count slides and verify their names")
    public void userCountsSlidesAndVerifytheirNames() throws IOException {
        slideCount = slidePage.getSlideCount();
        System.out.println("Total number of slides: " + slideCount);

        // Verify slide names
        boolean areNamesCorrect = slidePage.verifySlideNames();
        Assert.assertTrue(areNamesCorrect, "Slide names do not match with expected data!");
    }

    @Then("User verify duration of each slide")
    public void userVerifyDurationOfEachSlide() {
    	slidePage.measureSlideDurations(); 
    }
}
