package gerer_les_questions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajout_question_au_moins_deux_propositions_echec {

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
	public void addQuestionMinimumTwoPropositions() throws InterruptedException {

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

		// Wait for question add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-question-btn")));

		/********* Add question *********/
		// Click on add button
		driver.findElement(By.id("add-question-btn")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write description
		driver.findElement(
				By.xpath("//form[@class='ng-untouched ng-pristine ng-invalid']//div[@class='ql-editor ql-blank']"))
				.sendKeys("Les navigateurs sont-ils capables d'interpréter le TypeScript directement?");
		// Wait
		Thread.sleep(1000);

		// Select technology
		Select technology = new Select(driver.findElement(By.id("technology-add")));
		technology.selectByVisibleText("Angular");

		// Wait
		Thread.sleep(1000);

		// Select degree
		Select degree = new Select(driver.findElement(By.id("degree-add")));
		degree.selectByVisibleText("Intermédiaire");

		// Wait
		Thread.sleep(1000);

		// Click on add button
		driver.findElement(By.id("submit-add-question")).click();

		// Wait for alert to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Ok')]")));

		// Wait
		Thread.sleep(1000);

		// Click on ok button
		driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
