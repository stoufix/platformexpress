package gerer_les_questions;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chercher_une_question_succes {

	public WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		// Site to test
		driver.get("localhost:4200");
		// Maximizes the browser window
		driver.manage().window().maximize();
	}

	@Test
	public void searchQuestion() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for question menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("questions-management")));

		// Navigate to question section
		driver.findElement(By.id("questions-management")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for question search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-question")));

		/********* All questions *********/

		/********* Search question with result *********/
		// Write search term
		driver.findElement(By.id("search-question")).sendKeys("Angular 6");
		// Wait
		Thread.sleep(1000);

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[2]")));

		// Get the technology of first question of the search result
		String technologyQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyQuestion, "Angular 6");
		// Wait
		Thread.sleep(1000);

		/********* Search question without result *********/
		// Clear search term
		driver.findElement(By.id("search-question")).clear();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-question")).sendKeys("no-data-test");

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("no-data-available")));

		// Get the message of no data available
		String noData = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");

		// Wait
		Thread.sleep(1000);

		// clear search term
		driver.findElement(By.id("search-question")).clear();

		/********* Advanced Search question with result *********/
		// Wait
		Thread.sleep(1000);

		// Click on advanced search button
		driver.findElement(By.id("advanced-search-question-btn")).click();

		// Wait technology list to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("technologies-multiselect-dropdown")));

		// Open technologies search criteria
		driver.findElement(By.id("technologies-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all technologies
		driver.findElement(By.xpath(
				"//div[@id='allQuestions']//div[@class='card-body']//div[1]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all technologies
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='technologies-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='allQuestions']//div[@class='card-body']//div[1]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Angular");

		// Wait
		Thread.sleep(1000);

		// Select Angular technology
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='technologies-multiselect-dropdown']//div[contains(text(),'Angular')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Open degrees search criteria
		driver.findElement(By.id("degrees-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all degrees
		driver.findElement(By.xpath(
				"//div[@id='allQuestions']//div[@class='card-body']//div[1]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all degrees
		driver.findElement(By.xpath(
				"//div[@id='allQuestions']//div[@class='card-body']//div[1]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='allQuestions']//div[@class='card-body']//div[1]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Intermédiaire");

		// Wait
		Thread.sleep(1000);

		// Select degree
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='degrees-multiselect-dropdown']//div[contains(text(),'Intermédiaire')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Open creator search criteria
		driver.findElement(By.id("creator-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all creators
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all creators
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Maha Ben Said");

		// Wait
		Thread.sleep(1000);

		// Select creator
		driver.findElement(By.xpath("//div[contains(text(),'Maha Ben Said')]")).click();

		// Wait
		Thread.sleep(1000);

		// Open activity search criteria
		driver.findElement(By.id("activity-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Sécurité");

		// Wait
		Thread.sleep(1000);

		// Select activity
		driver.findElement(By.xpath("//div[contains(text(),'Sécurité')]")).click();

		// Wait
		Thread.sleep(1000);

		// Close activity search criteria
		driver.findElement(By.id("activity-multiselect-dropdown")).click();

		// Click on search button
		driver.findElement(By.id("search-question-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[2]")));

		// Get the technology of first question of the search result
		String technologyFirstQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyFirstQuestion, "Angular");

		// Get the degree of first question of the search result
		String degreeFirstQuestion = driver.findElement(By.xpath("//tr[1]//td[3]")).getText();

		// Compare it with the expected degree
		assertEquals(degreeFirstQuestion, "Intermédiaire");

		// Get the creator of first question of the search result
		String creatorFirstQuestion = driver.findElement(By.xpath("//tr[1]//td[5]")).getText();

		// Compare it with the expected creator
		assertEquals(creatorFirstQuestion, "Maha Ben Said");

		// Get the activity of first question of the search result
		String activityFirstQuestion = driver.findElement(By.xpath("//tr[1]//td[6]")).getText();

		// Compare it with the expected activity
		assertEquals(activityFirstQuestion, "Sécurité");

		/********* Advanced Search question without result *********/

		// Wait
		Thread.sleep(1000);

		// Open activity search criteria
		driver.findElement(By.id("activity-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all activities
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all activity
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='collapseInfo']//div[2]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Media");

		// Wait
		Thread.sleep(1000);

		// Select activity
		driver.findElement(By.xpath("//div[contains(text(),'Media')]")).click();

		// Wait
		Thread.sleep(1000);

		// Close activity search criteria
		driver.findElement(By.id("activity-multiselect-dropdown")).click();

		// Click on search button
		driver.findElement(By.id("search-question-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("no-data-available")));

		// Get no data available message
		noData = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");

		/********* My questions *********/

		/********* Search my questions with result *********/

		// Wait
		Thread.sleep(1000);

		// Navigate to my questions
		driver.findElement(By.id("myQuestion")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[2]")));

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-my-question")).sendKeys("Angular");

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[2]")));

		// Get the technology of first question of the search result
		String technologyMyQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyMyQuestion, "Angular");

		/********* Search my questions without result *********/

		// Wait
		Thread.sleep(1000);

		// clear search term
		driver.findElement(By.id("search-my-question")).clear();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-my-question")).sendKeys("no-data-test");

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("my-questions-no-data-available")));

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");

		// Wait
		Thread.sleep(1000);

		// Clear search term
		driver.findElement(By.id("search-my-question")).clear();

		/********* Advanced Search my questions with result *********/

		// Wait
		Thread.sleep(1000);

		// Wait technology list to display
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("my-question-technologies-multiselect-dropdown")));

		// Open technologies search criteria
		driver.findElement(By.id("my-question-technologies-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all technologies
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-technologies-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all technologies
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-technologies-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='myQuestions']//div[@class='row']//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Angular");

		// Wait
		Thread.sleep(1000);

		// Select Angular technology
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-technologies-multiselect-dropdown']//div[contains(text(),'Angular')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Open degrees search criteria
		driver.findElement(By.id("my-question-degrees-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='myQuestions']//div[@id='collapseInfo']//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Intermédiaire");

		// Wait
		Thread.sleep(1000);

		// Select degree
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-degrees-multiselect-dropdown']//div[contains(text(),'Intermédiaire')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Close degree search criteria
		driver.findElement(By.id("my-question-degrees-multiselect-dropdown")).click();

		// Click on search button
		driver.findElement(By.id("search-my-question-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[2]")));

		// Get the technology of first question of the search result
		String technologyMyFirstQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyMyFirstQuestion, "Angular");

		// Get the degree of first question of the search result
		String degreeMyFirstQuestion = driver.findElement(By.xpath("//tr[1]//td[3]")).getText();

		// Compare it with the expected degree
		assertEquals(degreeMyFirstQuestion, "Intermédiaire");

		/********* Advanced Search my question without result *********/

		// Wait
		Thread.sleep(1000);

		// Open activity search criteria
		driver.findElement(By.id("my-question-degrees-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all degrees
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-degrees-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all degrees
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-degrees-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='myQuestions']//div[@id='collapseInfo']//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Standard");

		// Wait
		Thread.sleep(1000);

		// Select degree
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='my-question-degrees-multiselect-dropdown']//div[contains(text(),'Standard')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Close degree search criteria
		driver.findElement(By.id("my-question-degrees-multiselect-dropdown")).click();

		// Click on search button
		driver.findElement(By.id("search-my-question-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("my-questions-no-data-available")));
		
		// Get no data available message
		noData = driver.findElement(By.id("my-questions-no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
