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

public class Modifier_une_question_succes {

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
	public void updateQuestion() throws InterruptedException {

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

		// Wait for question edit button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[7]//button[2]")));

		/********* Edit question *********/
		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();

		// Wait for question update modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editmodal")));

		// Clear description field
		driver.findElement(By.xpath("//div[@class='ql-editor']")).clear();
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.xpath("//div[@class='ql-editor']")).sendKeys("Angular est un framework orienté");

		// Wait
		Thread.sleep(1000);

		// Click to delete first proposition
		driver.findElement(By.xpath("//td[2]//button[1]")).click();

		// Wait
		Thread.sleep(1000);

		// Wait for proposition update button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[1]//button[1]")));

		// Click to update proposition
		driver.findElement(By.xpath("//td[1]//button[1]")).click();

		// Wait
		Thread.sleep(1000);

		// Wait for confirm proposition update button to display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='col-9']//button[@type='button']")));

		// Clear proposition field
		driver.findElement(By.id("description")).clear();

		// Write proposition
		driver.findElement(By.id("description")).sendKeys("Composants");

		// Wait
		Thread.sleep(1000);

		// click on confirm proposition update button
		driver.findElement(By.xpath("//div[@class='col-9']//button[@type='button']")).click();

		// Wait
		Thread.sleep(1000);

		// Click to delete first proposition
		driver.findElement(By.xpath("//td[2]//button[1]")).click();

		// Write first proposition
		driver.findElement(By.id("update-question-proposition-add")).sendKeys("Composants");

		// Wait
		Thread.sleep(1000);

		// Click to add first proposition
		driver.findElement(By.id("update-question-add-proposition-btn")).click();

		// Wait
		Thread.sleep(1000);

		// Write second proposition
		driver.findElement(By.id("update-question-proposition-add")).sendKeys("Modules");

		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("update-question-valid")).click();

		// Click to add second proposition
		driver.findElement(By.id("update-question-add-proposition-btn")).click();

		// click on update button
		driver.findElement(By.id("submit-update-question")).click();

		// Wait
		Thread.sleep(1000);

		/********* Verify the edit of question *********/

		// Wait list of questions to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the description of first question of the list
		String descQuestion = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected description
		assertEquals(descQuestion, "Angular est un framework orienté");

		// Get the technology of first question of the list
		String technologyQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected technology
		assertEquals(technologyQuestion, "Angular");

		// Get the degree of first question of the list
		String degreeQuestion = driver.findElement(By.xpath("//tr[1]//td[3]")).getText();

		// Compare it with the expected degree
		assertEquals(degreeQuestion, "Intermédiaire");

		// Get the creator of first question of the list
		String creatorQuestion = driver.findElement(By.xpath("//tr[1]//td[5]")).getText();

		// Compare it with the expected creator
		assertEquals(creatorQuestion, "Maha Ben Said");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
