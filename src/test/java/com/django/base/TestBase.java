package com.django.base;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.django.utils.Constants;
import com.django.utils.UtilityFunctions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	Logger log = Logger.getLogger(TestBase.class);
	protected ChromeDriver driver;

	@BeforeClass
	public void setUp() throws Exception {

		try {
			log.info("Started the basic setUp");
			// Set the Driver
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

			// driver.get method used to navigate to website
			driver.get(Constants.BASE_URL);

			// maximize browser size
			driver.manage().window().maximize();

			// Check we are in right page and loaded
			Assert.assertEquals(Constants.HOME_PAGE_TITLE, driver.getTitle());
			log.info("Finished opening the web page after launching the browser");
		} catch (Exception e) {
			log.error("Error in Base class,set up fucntion " + e.getMessage());
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		try {
			
			driver.quit();
		} catch (Exception e) {
			log.error("Error in Base class,tear down" + e.getMessage());
		}
	}

	@AfterSuite
	public void createReports() {
		try {
			log.info("Started the creating the Reports");
			new UtilityFunctions().createTestReports();
			log.info("Ended creating the reports");

		} catch (Exception e) {
			log.error("Error in Base class, creating reports" + e.getMessage());
		}
	}

}
