package hooks;

import io.cucumber.java.BeforeAll;
import java.io.File;

public class ReportCleaner {

    @BeforeAll 
    public static void cleanUpReports() {
    	System.out.println("Clean old reports before execution...");

        // Delete Extent Report index.html
        deleteFile("target/extent-report/index.html");

        // Delete Cucumber Reports
        deleteFile("test-output/CucumberReport.html");
        deleteFile("test-output/CucumberReport.json");
        System.out.println("Old reports deleted.");
    }
    
 // Delete single file
    private static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted: " + filePath);
            } else {
                System.out.println("not deleted: " + filePath);
            }
        }
    }
}


