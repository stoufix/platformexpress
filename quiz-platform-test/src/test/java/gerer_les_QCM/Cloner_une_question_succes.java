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

public class Cloner_une_question_succes {

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
	public void cloneQuestion() throws InterruptedException {

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

		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();

		// Wait for question clone button to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[3]//span[2]//button[1]")));

		/********* Clone question *********/
		// Click on clone button
		driver.findElement(By.xpath("//tr[1]//td[3]//span[2]//button[1]")).click();

		// Wait for question clone modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("clonemodal")));

		// Clear description field
		driver.findElement(By.xpath("//div[@class='ql-editor']")).clear();
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.xpath("//div[@class='ql-editor']")).sendKeys("Angular est un framework orienté");

		// Wait
		Thread.sleep(1000);

		// Write mark
		driver.findElement(By.id("mark-clone-question")).sendKeys("2");
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

		// Click on confirm proposition update button
		driver.findElement(By.xpath("//div[@class='col-9']//button[@type='button']")).click();

		// Wait
		Thread.sleep(1000);

		// Click to delete first proposition
		driver.findElement(By.xpath("//td[2]//button[1]")).click();

		// Write first proposition
		driver.findElement(By.id("clone-question-proposition-add")).sendKeys("Composants");

		// Wait
		Thread.sleep(1000);

		// Click to add first proposition
		driver.findElement(By.id("clone-question-add-proposition-btn")).click();

		// Wait
		Thread.sleep(1000);

		// Write second proposition
		driver.findElement(By.id("clone-question-proposition-add")).sendKeys("Modules");

		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("clone-question-valid")).click();

		// Click to add second proposition
		driver.findElement(By.id("clone-question-add-proposition-btn")).click();

		// Wait
		Thread.sleep(1000);

		// Click on clone button
		driver.findElement(By.id("submit-clone-question")).click();

		// Wait
		Thread.sleep(1000);

		/********* Verify the clone of question *********/

		// Wait list of questions to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the description of first question of the list
		String descQuestion = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected description
		assertEquals(descQuestion, "Angular est un framework orienté");

		// Get the creator of first question of the list
		String creatorQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected creator
		assertEquals(creatorQuestion, "Maha Ben Said");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
