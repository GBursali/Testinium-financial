Test Framework for CatchyLabs' Money Transfer Website
---
This repository is a test framework designed for testing the CatchLabs' Money Transfer website. Built primarily with
Java, Selenium, and RestAssured, the framework integrates several tools and custom utilities to streamline automated
testing.

---
Features
---

- **Web Testing:** Built using Selenium for robust browser automation.
- **API Testing:** Includes RestAssured for testing RESTful APIs.
- **Custom Utilities:** Extensions and utilities to simplify test development, code design and such.
- **Extensibility:** Easily adaptable to additional test cases or integrations.
- **Bug-Tolerant:** Framework accounts for existing bugs in the application being tested.

---
Installation Instructions
---

1. Clone the Repository:
   ```bash
       git clone https://github.com/GBursali/Testinium-financial
       cd Testinium-financial
   ```
2. Set Up Java Environment:
   1. Ensure you have Java 21 or later installed. 
   2. Set the JAVA_HOME environment variable.
3. Install Maven:
   1. Download and install Maven from Maven Downloads.
   2. Verify installation by running:``mvn -version``
4. Dependencies:
   1. All required dependencies are managed through the ``pom.xml`` file.
   2. Run the following to download and install dependencies: ``mvn clean install``
5. Create Credentials File:
   1. Navigate to the ``src/test/resources/json`` directory.
   2. Locate the ``credentials.example.json`` file.
   3. Replace `YourUsername` and `YourPassword` with the actual credentials for the application.
   4. Rename the file to ``credentials.json``.

---
Usage Guidelines
---
1. Running Tests:
   1. To execute all tests, run:
   
      ``mvn -P Full clean verify``
   2. If you want specific tags or tests to be executed: 
      1. Add your tags or other settings to the ``src/test/java/cl/testinium/runners/CukesRunner``
      2. Then you can run:
        
         ``mvn -P Cukes clean verify``
   3. If you don't want to receive a report on the results, use:
   
         ``mvn -P Full clean test``
2. Test Reports:
   1. After the tests are executed, a report will be generated in the ``target/cucumber-html-reports`` directory.
   2. To view the report, open the `overview-features.html` file in your web browser.
3. Customizing Tests:
   1. Modify the settings in the ``src/test/resources/json/settings.json`` file to change the base URLs, browser, or any other configuration settings.
---
Troubleshooting Tips
---

- **Credentials File Missing:**
  - Ensure that the ``credentials.json`` file is present in the ``src/test/resources/json`` directory.
  - If the file is missing, create it and fill it with the required credentials.
- **Tests Failing:**
  - Note that the application under test contains known bugs, so some test failures are expected.
  - If you see unexpected failures, check the logs for details about the failures.
  - If the issue persists, please open an issue in the GitHub repository.
- **Dependency Issues:**
  - If you get an error about a missing dependency, check the logs for details about the failure.
  - Run ``mvn dependency:resolve`` to resolve any missing dependencies.
- **Browser Compatibility:**
  - Make sure the correct WebDriver binaries are available in your system PATH (e.g., ChromeDriver, GeckoDriver).
--- 
Contributing
---
  Contributions are welcome! Please fork the repository and submit a pull request with detailed information about your changes.

---
License
---
This project is licensed under the Apache 2.0 License. See the `LICENSE` file for details.

---
## Note
While we strive for stability, test failures due to application bugs are to be expected. Please report any framework issues via the GitHub Issues tab.
