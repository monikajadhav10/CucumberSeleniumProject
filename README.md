**Tools & Technologies**

1. Programming Language: Java
2. Test Automation Framework: Selenium WebDriver
3. BDD Framework: Cucumber
4. Test Runner: TestNG
5. Build Tool: Maven
6. Reporting: Extent Reports(to attach output report) and Cucumber HTML report

**Features Implemented**

1. Shop Navigation (shopNavigation.feature)
2. Count Slides & Verify Names (countSlides.feature)
3. Footer Links Validation (footerLinks.feature)

**Reports & Logs**

1. Cucumber Reports: target/cucumber-reports/
2. Extent Reports: target/extent-report/index.html

**More details on test framework**
This project follows a Hybrid Test Automation Framework that combines:

- Behavior-Driven Development (BDD) with Cucumber
- Page Object Model (POM) for better maintainability
- TestNG as the test runner
- Maven for dependency and build management

**Parallel Execution**
The framework is designed to run feature files in parallel using TestNG and Cucumber.

 **How It Works?**
- Cucumber Hooks manages test setup and teardown.
- TestNG's parallel execution is controlled via testng.xml and Maven parameters.
- Dynamic Runner Generator creates test runners at runtime.
- WebDriverManager ensures proper driver lifecycle handling.

**Setup**
1. Clone the Repository
git clone https://github.com/<username>/NewSeleniumProject.git

cd NewSeleniumProject

3. Install Dependencies
mvn clean install

4. Make sure you have Java installed.

**Run Tests**
mvn test-compile exec:java -Dexec.classpathScope=test -Dexec.mainClass="runners.DynamicRunnerGenerator" (As I'm using dynamic test runner generator)
mvn clean test -Dparallel=tests -DthreadCount=3

**Author**
Monika Jadhav
