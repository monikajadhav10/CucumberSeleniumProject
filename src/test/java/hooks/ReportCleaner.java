package hooks;

import io.cucumber.java.BeforeAll;
import java.io.File;

public class ReportCleaner {

    @BeforeAll
    public static void cleanUpReports() {
        System.out.println("Cleaning old reports...");

        // Delete all .html and .json files from /target/cucumber-reports
        deleteFilesByExtension("target/cucumber-reports", ".html");
        deleteFilesByExtension("target/cucumber-reports", ".json");

        // Delete Extent Report index.html from /target/extent-report
        deleteFile("target/extent-report/index.html");

        System.out.println("Cleanup complete.");
    }

    // Method to delete a specific file
    private static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Deleted: " + filePath);
                } else {
                    System.out.println("Could not delete: " + filePath);
                }
            }
        } catch (Exception e) {
            System.out.println("Error deleting file: " + filePath + " - " + e.getMessage());
        }
    }

    // Method to delete all files with a specific extension in a folder
    private static void deleteFilesByExtension(String folderPath, String extension) {
        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                System.out.println("Folder not found: " + folderPath);
                return;
            }

            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(extension)) {
                        deleteFile(file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing folder: " + folderPath + " - " + e.getMessage());
        }
    }
}
