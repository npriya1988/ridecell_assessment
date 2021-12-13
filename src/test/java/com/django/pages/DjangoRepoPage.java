package com.django.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.django.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DjangoRepoPage {

	Logger log = Logger.getLogger(DjangoRepoPage.class);
	// Declare the driver
	protected WebDriver driver;

	@FindBy(xpath = "//li[@data-tab-item='org-header-repositories-tab']//a[@class='UnderlineNav-item ']")
	private WebElement repositoriesTab;

	@FindBy(xpath = "//li[@class='Box-row']")
	private List<WebElement> listOfRepos;

	private String repositoriesName = "(//div[@class='flex-auto']//a[@data-hovercard-type='repository'])[%s]";
	private String repositoriesDescriptions = "(//div[@class='flex-auto']//a[@href='/django/%s'])/parent::h3//following-sibling::p";

	public void repositoriesTab() {
		repositoriesTab.click();
	}

	public WebElement getRepoName(int repoName) {
		return driver.findElement(By.xpath(String.format(repositoriesName, repoName)));
	}

	public WebElement getRepoDescription(String repoName) {
		return driver.findElement(By.xpath(String.format(repositoriesDescriptions, repoName)));
	}

	public int isdescriptionExist(String repoName) {
		return driver.findElements(By.xpath(String.format(repositoriesDescriptions, repoName))).size();
	}
	
	public void closeDriver() {
		driver.close();
	}

	// Instantiate the driver for this class
	public DjangoRepoPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		log.info("Elements are get initated");
		}
	
	
	/**
	 * @author nithya
	 * @param
	 * @actions once navigate to repo page, fetch repo name and its description from
	 *          the page
	 * @return map which has key as repository name and value as description
	 */
	public Map<String, String> getReposAndDeceriptionFromPage() {
		// Create the Map for Repo and description
		Map<String, String> repositories = new HashMap<String, String>();
		try {
			// Click on the Repository tab
			repositoriesTab.click();

			// Fetch the Repo Details
			List<WebElement> repso = listOfRepos;

			// Filter only Repo and Description
			for (int i = 1; i <= repso.size(); i++) {
				String repoName = getRepoName(i).getText().trim();

				String repoDescription = isdescriptionExist(repoName) > 0 ? getRepoDescription(repoName).getText()
						: Constants.ZERO;
				repositories.put(repoName, repoDescription);
			}
			
			closeDriver();
			log.info("Successfully Webpage data are fetch from page");
		} catch (Exception e) {
			log.error("Error in Webpage of Django " + e.getMessage());
		}

		return repositories;
	}

	/**
	 * 
	 * @param jsonArray
	 * @return map which has key as repository name and value as description
	 */
	public Map<String, String> getRepoUsingAPI(JsonArray jsonArray) {
		Map<String, String> repoUsingApi = new HashMap<String, String>();
		try {
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
				String repoNameAPI = jsonObject.get(Constants.REPO_NAME).getAsString();
				String repoDescriptionAPI = !jsonObject.get(Constants.REPO_DESCRIPTION).isJsonNull()
						? jsonObject.get(Constants.REPO_DESCRIPTION).getAsString()
						: Constants.ZERO;
				repoUsingApi.put(repoNameAPI, repoDescriptionAPI);
			}
			log.info("Successfully API data are fetched");
		} catch (Exception e) {
			log.error("Error in API of Django " + e.getMessage());
		}
		return repoUsingApi;
	}

}
