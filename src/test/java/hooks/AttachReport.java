package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class AttachReport {

    @After
    public void attachFileToReport(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@AttachReport")) {
            try {
                File file = new File("target/jacket_details.txt");

                if (!file.exists() || file.length() == 0) {
                    System.out.println("Jacket details file is missing or empty. Skipping attachment.");
                    return;
                }

                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                ExtentCucumberAdapter.getCurrentStep().info("Jacket Details: <pre>" + content + "</pre>");

                System.out.println("Attached jacket_details.txt to the Extent Report.");

            } catch (IOException e) {
                System.out.println("Error attaching file: " + e.getMessage());
            }
        }
    }
}
