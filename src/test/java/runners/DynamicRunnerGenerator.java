package runners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicRunnerGenerator {
    private static final String FEATURE_DIR = "src/test/resources/features";
    private static final String RUNNER_DIR = "src/test/java/runners/";

    public static void main(String[] args) throws IOException {
        File featureDir = new File(FEATURE_DIR);
        if (!featureDir.exists()) {
            throw new RuntimeException("Feature directory not found: " + FEATURE_DIR);
        }

        File runnerDir = new File(RUNNER_DIR);
        if (!runnerDir.exists()) {
            runnerDir.mkdirs();
        }

        List<String> featureFiles = new ArrayList<>();
        File[] files = featureDir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".feature")) {
                    featureFiles.add(file.getName());
                }
            }
        }


        for (String feature : featureFiles) {
            createRunnerForFeature(feature);
        }
    }

    private static void createRunnerForFeature(String feature) throws IOException {
        String className = feature.replace(".feature", "Runner");
        String featurePath = FEATURE_DIR + "/" + feature;
        String content = generateRunnerClassContent(className, featurePath);

        File file = new File(RUNNER_DIR + className + ".java");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
        System.out.println("Generated Runner Class: " + className);
    }

    private static String generateRunnerClassContent(String className, String featurePath) {
        return "package runners;\n\n" +
                "import io.cucumber.testng.AbstractTestNGCucumberTests;\n" +
                "import io.cucumber.testng.CucumberOptions;\n\n" +
                "@CucumberOptions(\n" +
                "        features = \"" + featurePath + "\",\n" +
                "        glue = {\"stepDefinitions\", \"hooks\"},\n" +
                "        plugin = {\n" +
                "                \"pretty\",\n" +
                "                \"html:target/cucumber-reports/" + className + ".html\",\n" +
                "                \"json:target/" + className + ".json\",\n" +
                "                \"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:\"\n" +
                "        },\n" +
                "        monochrome = true\n" +
                ")\n" +
                "public class " + className + " extends AbstractTestNGCucumberTests {}";
    }
}
