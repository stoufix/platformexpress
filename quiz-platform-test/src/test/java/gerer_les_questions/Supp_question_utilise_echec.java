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

public class Supp_question_utilise_echec {

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
	public void deleteQuestionFail() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		/********* Find used question in quizzes list *********/
		// Wait for quizzes menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quizzes-management")));
		// Navigate to quizzes section
		driver.findElement(By.id("quizzes-management")).click();

		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Open details of first quiz
		driver.findElement(By.xpath("//tr[1]//td[7]//button[1]")).click();

		// Wait for details modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("detailsmodal")));

		// Get the first question of the quiz
		String question = driver
				.findElement(By.xpath("//p[contains(text(),'Java est un langage développé par?')]"))
				.getText();

		// Close details modal
		driver.findElement(By.id("exit-detail-quiz")).click();

		// Wait
		Thread.sleep(1000);

		/********* Show question list *********/
		// Wait for question menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("questions-management")));

		// Navigate to questions section
		driver.findElement(By.id("questions-management")).click();

		/********* Search for question to delete *********/
		// Wait for question search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-question")));

		// Write search term with the description of question found in the first quiz of
		// the list
		driver.findElement(By.id("search-question")).sendKeys(question);

		// Wait
		Thread.sleep(1000);

		/********* delete question *********/
		// Wait for delete button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[7]//button[3]")));

		// Click on delete button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[3]")).click();

		// Wait confirm button to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));

		// Click on confirm button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();

		// Wait alert to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Ok')]")));

		// Click on ok button
		driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();

		/********* Verify the non deleting of degree *********/
		// Wait for question search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-question")));

		// clear search term
		driver.findElement(By.id("search-question")).clear();

		// Write search term with the description of question found in the first quiz of
		// the list
		driver.findElement(By.id("search-question")).sendKeys(question);

		// Wait
		Thread.sleep(1000);

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first degree of the search result
		String questionDescription = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(questionDescription, question);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
