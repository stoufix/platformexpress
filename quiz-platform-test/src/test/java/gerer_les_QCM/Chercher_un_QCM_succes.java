package gerer_les_QCM;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chercher_un_QCM_succes {

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
	public void searchQuiz() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for quiz menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quizzes-management")));

		// Navigate to quiz section
		driver.findElement(By.id("quizzes-management")).click();

		// Wait for quiz search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-quiz")));

		/********* All quizzes *********/

		/********* Search quiz with result *********/
		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-quiz")).sendKeys("QCM Java Débutant");

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[2]")));

		// Get the title of first quiz of the search result
		String titleQuiz = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected technology
		assertEquals(titleQuiz, "QCM Java Débutant");

		/********* Search quiz without result *********/

		// Wait
		Thread.sleep(1000);

		// Clear search term
		driver.findElement(By.id("search-quiz")).clear();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-quiz")).sendKeys("no-data-test");

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("no-data-available-table")));

		// Get the message of no data available
		String noData = driver.findElement(By.id("no-data-available-table")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");

		// Wait
		Thread.sleep(1000);

		// clear search term
		driver.findElement(By.id("search-quiz")).clear();

		/********* Advanced Search quiz with result *********/

		// Wait
		Thread.sleep(1000);

		// Click on advanced search button
		driver.findElement(By.id("advanced-search-quiz-btn")).click();

		// Wait technology list to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("technologies-multiselect-dropdown")));

		// Open technologies search criteria
		driver.findElement(By.id("technologies-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all technologies
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='technologies-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all technologies
		driver.findElement(By.xpath(
				"//div[@id='allQCM']//div[@class='card-body']//div[1]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='allQCM']//div[@class='card-body']//div[1]//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Java");

		// Wait
		Thread.sleep(1000);

		// Select Java technology
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='technologies-multiselect-dropdown']//div[contains(text(),'Java')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Open degrees search criteria
		driver.findElement(By.id("degrees-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all degrees
		driver.findElement(By.xpath(
				"//div[@id='allQCM']//div[@class='card-body']//div[1]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all degrees
		driver.findElement(By.xpath(
				"//div[@id='allQCM']//div[@class='card-body']//div[1]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[1]//div[1]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='allQCM']//div[@class='card-body']//div[1]//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Débutant");

		// Wait
		Thread.sleep(1000);

		// Select degree
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='degrees-multiselect-dropdown']//div[contains(text(),'Débutant')]"))
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
		driver.findElement(By.id("search-quiz-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[3]")));

		// Get the technology of first quiz of the search result
		String technologyFirstQuiz = driver.findElement(By.xpath("//tr[1]//td[3]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyFirstQuiz, "Java");

		// Get the degree of first quiz of the search result
		String degreeFirstQuiz = driver.findElement(By.xpath("//tr[1]//td[4]")).getText();

		// Compare it with the expected degree
		assertEquals(degreeFirstQuiz, "Débutant");

		// Get the creator of first quiz of the search result
		String creatorFirstQuiz = driver.findElement(By.xpath("//tr[1]//td[5]")).getText();

		// Compare it with the expected creator
		assertEquals(creatorFirstQuiz, "Maha Ben Said");

		// Get the activity of first quiz of the search result
		String activityFirstQuiz = driver.findElement(By.xpath("//tr[1]//td[6]")).getText();

		// Compare it with the expected activity
		assertEquals(activityFirstQuiz, "Sécurité");

		/********* Advanced Search quiz without result *********/

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
		driver.findElement(By.id("search-quiz-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("no-data-available-table")));

		// Get no data available message
		noData = driver.findElement(By.id("no-data-available-table")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");

		/********* My quizzes *********/

		/********* Search my quizzes with result *********/

		// Wait
		Thread.sleep(1000);

		// Navigate to my quizzes
		driver.findElement(By.id("my-quiz-menu")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[3]")));

		// Wait
		Thread.sleep(1000);
//
//		// Write search term
//		driver.findElement(By.id("search-quiz-by-user")).sendKeys("Débutant");
//
//		// Wait result to display
//		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[4]")));
//
//		// Get the degree of first quiz of the search result
//		String degreeMyQuiz = driver.findElement(By.xpath("//tr[1]//td[4]")).getText();
//
//		// Compare it with the expected degree
//		assertEquals(degreeMyQuiz, "Débutant");

		/********* Search my quizzes without result *********/

//		// Wait
//		Thread.sleep(1000);
//
//		// clear search term
//		driver.findElement(By.id("search-quiz-by-user")).clear();
//
//		// Wait
//		Thread.sleep(1000);
//
//		// Write search term
//		driver.findElement(By.id("search-quiz-by-user")).sendKeys("no-data-test");
//
//		// Wait result to display
//		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quiz-by-user-no-data-available")));
//
//		// Get no data available message
//		noData = driver.findElement(By.id("quiz-by-user-no-data-available")).getText();
//
//		// Compare no data message with the expected message
//		assertEquals(noData, "Aucune donnée trouvée.");
//
//		// Wait
//		Thread.sleep(1000);
//
//		// Clear search term
//		driver.findElement(By.id("search-quiz-by-user")).clear();

		/********* Advanced Search my quizzes with result *********/

		// Wait
		Thread.sleep(1000);

		// Wait technology list to display
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("quiz-by-user-technologies-multiselect-dropdown")));

		// Open technologies search criteria
		driver.findElement(By.id("quiz-by-user-technologies-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all technologies
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-technologies-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all technologies
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-technologies-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@class='tab-pane active show']//div[@class='row']//div[1]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Java");

		// Wait
		Thread.sleep(1000);

		// Select Angular technology
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-technologies-multiselect-dropdown']//div[contains(text(),'Java')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Open degrees search criteria
		driver.findElement(By.id("quiz-by-user-degrees-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='myQCM']//div[@id='collapseInfo']//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Débutant");

		// Wait
		Thread.sleep(1000);

		// Select degree
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-degrees-multiselect-dropdown']//div[contains(text(),'Débutant')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Close degree search criteria
		driver.findElement(By.id("quiz-by-user-degrees-multiselect-dropdown")).click();

		// Click on search button
		driver.findElement(By.id("search-quiz-by-user-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[3]")));

		// Get the technology of first quiz of the search result
		String technologyMyFirstQuiz = driver.findElement(By.xpath("//tr[1]//td[3]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyMyFirstQuiz, "Java");

		// Get the degree of first quiz of the search result
		String degreeMyFirstQuiz = driver.findElement(By.xpath("//tr[1]//td[4]")).getText();

		// Compare it with the expected degree
		assertEquals(degreeMyFirstQuiz, "Débutant");

		/********* Advanced Search my quiz without result *********/

		// Wait
		Thread.sleep(1000);

		// Open degree search criteria
		driver.findElement(By.id("quiz-by-user-degrees-multiselect-dropdown")).click();

		// Wait
		Thread.sleep(1000);

		// Select all degrees
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-degrees-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Unselect all degrees
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-degrees-multiselect-dropdown']//ul[@class='item1']//li[@class='multiselect-item-checkbox']"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.xpath(
				"//div[@id='myQCM']//div[@id='collapseInfo']//div[2]//ng-multiselect-dropdown[1]//div[1]//div[2]//ul[1]//li[2]//input[1]"))
				.sendKeys("Standard");

		// Wait
		Thread.sleep(1000);

		// Select degree
		driver.findElement(By.xpath(
				"//ng-multiselect-dropdown[@id='quiz-by-user-degrees-multiselect-dropdown']//div[contains(text(),'Standard')]"))
				.click();

		// Wait
		Thread.sleep(1000);

		// Close degree search criteria
		driver.findElement(By.id("quiz-by-user-degrees-multiselect-dropdown")).click();

		// Click on search button
		driver.findElement(By.id("search-quiz-by-user-btn")).click();

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quiz-by-user-no-data-available")));

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
