package gerer_les_questions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modif_question_au_moins_deux_propositions_echec {

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
	public void UpdateQuestionMinimumTwoPropositions() throws InterruptedException {

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

		// Wait for question edit button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[7]//button[2]")));

		/********* Edit question *********/
		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for question update modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editmodal")));

		// Remove second proposition
		driver.findElement(By.xpath("//li[2]//div[1]//div[3]//td[2]//button[1]")).click();
		// Wait
		Thread.sleep(1000);

		// Remove first proposition
		driver.findElement(By.xpath("//li[1]//div[1]//div[3]//td[2]//button[1]")).click();
		// Wait
		Thread.sleep(1000);

		// Click on update button
		driver.findElement(By.id("submit-update-question")).click();
		// Wait
		Thread.sleep(1000);

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
