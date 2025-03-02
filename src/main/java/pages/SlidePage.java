package pages;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SlidePage extends BasePage {

	@FindBy(xpath = "//button[contains(@class, 'TileHeroStories_tileHeroStoriesButton')]")
	private List<WebElement> slideElements;

	@FindBy(xpath = "//button[contains(@class, 'TileHeroStories_tileHeroStoriesButton')]//div[contains(@class, 'TileHeroStories_tileHeroStoriesButtonTitle')]")
	private List<WebElement> slideTitleElements;

	public SlidePage() {
		super();
	}

	public int getSlideCount() {
		return slideElements.size();
	}

	public List<String> getActualSlideNames() {
		List<String> slideNames = new ArrayList<>();

		for (WebElement slide : slideTitleElements) {
            System.out.println("Slide: " + slide.getAttribute("innerHTML"));
			String slideText = slide.getText().trim();
			System.out.println("Extracted Slide Name: " + slideText);
			slideNames.add(slideText);
		}
		return slideNames;
	}

	public List<String> getExpectedSlideNames() throws IOException {
		String content = new String(Files.readAllBytes(Paths
				.get("/Users/monika/eclipse-workspace/NewSeleniumProject/src/test/resources/testdata/slideData.json")));
		JSONObject json = new JSONObject(content);
		JSONArray slidesArray = json.getJSONArray("expectedSlides");

		List<String> slideNames = new ArrayList<>();
		for (int i = 0; i < slidesArray.length(); i++) {
			slideNames.add(slidesArray.getString(i));
		}
		return slideNames;
	}

	public boolean verifySlideNames() throws IOException {
		List<String> expectedSlideNames = getExpectedSlideNames();
		List<String> actualSlideNames = getActualSlideNames();
		return actualSlideNames.equals(expectedSlideNames);
	}

	public void measureSlideDurations() {
//	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Map<String, Long> slideDurations = new LinkedHashMap<>();
	    long startTime, endTime;

	    List<String> slideNames = getActualSlideNames(); // Get slide names

	    if (slideElements.isEmpty() || slideNames.isEmpty()) {
	        System.out.println("No slides found!");
	        return;
	    }

	    WebElement firstSlide = slideElements.get(0); // Store Slide 1 for later

	    // Measure Slide 2 onward first
	    for (int i = 1; i < slideElements.size(); i++) { // Start from Slide 2
	        WebElement slide = slideElements.get(i);
	        String slideName = slideNames.get(i); // Fetch name using index

	        wait.until(ExpectedConditions.attributeToBe(slide, "aria-selected", "true"));
	        startTime = System.currentTimeMillis();

	        wait.until(ExpectedConditions.attributeToBe(slide, "aria-selected", "false"));
	        endTime = System.currentTimeMillis();

	        slideDurations.put(slideName, (endTime - startTime) / 1000);
	    }

	    // Now measure Slide 1 at the end
	    if (firstSlide != null) {
	        String firstSlideName = slideNames.get(0); // Fetch Slide 1 name from list

	        wait.until(ExpectedConditions.attributeToBe(firstSlide, "aria-selected", "true"));
	        startTime = System.currentTimeMillis();

	        wait.until(ExpectedConditions.attributeToBe(firstSlide, "aria-selected", "false"));
	        endTime = System.currentTimeMillis();

	        slideDurations.put(firstSlideName, (endTime - startTime) / 1000);
	    }

	    // Print the results
	    slideDurations.forEach((slide, duration) ->
	        System.out.println("Slide '" + slide + "' was displayed for " + duration + " seconds.")
	    );
	}

}
