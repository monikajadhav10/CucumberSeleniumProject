package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FooterPage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FooterSteps {
	FooterPage footerPage;

	public FooterSteps() {
		this.footerPage = new FooterPage();
	}

	@When("User scroll down to footer")
	public void userScrollDownToFooter() {
		footerPage.scrollToFooter();
	}

	@Then("User read all the footer links under each section")
	public void userReadAllTheFooterLinksUnderEachSection() {
		Map<String, List<String>> footerLinksBySection = footerPage.getFooterLinksBySection();

		for (Map.Entry<String, List<String>> entry : footerLinksBySection.entrySet()) {
			System.out.println("Section: " + entry.getKey());
			for (String link : entry.getValue()) {
				System.out.println("   - " + link);
			}
		}
	}

	@Then("User check for duplicate links in the footer")
	public void userCheckForDuplicateLinksInTheFooter() {
		Map<String, List<String>> footerLinksBySection = footerPage.getFooterLinksBySection();
		Set<String> uniqueLinks = new HashSet<>();
		List<String> duplicates = new ArrayList<>();

		for (List<String> links : footerLinksBySection.values()) {
			for (String link : links) {
				if (!uniqueLinks.add(link)) {
					duplicates.add(link);
				}
			}
		}

		if (!duplicates.isEmpty()) {
			System.out.println("Duplicate Links Found:");
			duplicates.forEach(System.out::println);
		} else {
			System.out.println("No duplicate links found.");
		}
	}

	@Then("User save all the footer links in a CSV file")
	public void userSaveAllTheFooterLinksInACsvFile() {
		Map<String, List<String>> footerLinksBySection = footerPage.getFooterLinksBySection();
		String directoryPath = "/Users/monika/eclipse-workspace/NewSeleniumProject/test-output";
		String filePath = directoryPath + "/footer_links.csv";

		try {
			// check directory exists
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
				System.out.println("Created directory: " + directoryPath);
			}

			// Write data to CSV
			try (FileWriter writer = new FileWriter(filePath)) {
				writer.append("Section,Link Name\n"); // CSV Header

				for (Map.Entry<String, List<String>> entry : footerLinksBySection.entrySet()) {
					for (String link : entry.getValue()) {
						writer.append(entry.getKey()).append(",").append(link).append("\n");
					}
				}

				System.out.println("CSV file saved: " + filePath);
			}

		} catch (IOException e) {
			System.err.println("Error saving CSV file: " + e.getMessage());
		}
	}
}
