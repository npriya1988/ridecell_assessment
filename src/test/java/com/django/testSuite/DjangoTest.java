package com.django.testSuite;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.django.base.TestBase;
import com.django.pages.DjangoRepoPage;
import com.django.utils.Constants;
import com.google.gson.JsonArray;

public class DjangoTest extends TestBase {

	Logger log = Logger.getLogger(DjangoTest.class);
	DjangoRepoPage djangoPage;

	@BeforeMethod
	public void initWorkFlow() throws IOException {

		// Initialize work flow
		djangoPage = new DjangoRepoPage(driver);
	}

	/**
	 * @author innpriya021
	 * Validate the data from page and using ApI are equal
	 */
	@Test
	public void validateRepoNameFromPageAndAPI() {
		try {
			log.info("Started the validation for webpage");
			Map<String, String> repoDetailsFromPage = djangoPage.getReposAndDeceriptionFromPage();
			log.info("Started the validate for API");
			Map<String, String> repoDetailsFromAPI = djangoPage
					.getRepoUsingAPI(given().get(Constants.API_URL).as(JsonArray.class));

			// Assert the Repo name and description are same in Page and from API
			Assert.assertEquals(repoDetailsFromPage, repoDetailsFromAPI);
			log.info("Test is completed");
		} catch (Exception e) {
			log.error("Error in  test " + e.getMessage());
		}
	}

}
