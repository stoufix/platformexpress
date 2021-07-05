package gerer_les_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Cloner_question_au_moins_deux_propositions_echec {

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
	public void CloneQuestionMinimumTwoPropositions() throws InterruptedException {

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

		// Write mark
		driver.findElement(By.id("mark-clone-question")).sendKeys("2");

		// Wait
		Thread.sleep(1000);

		// Remove second proposition
		driver.findElement(By.xpath("//li[2]//div[1]//div[3]//td[2]//button[1]")).click();

		// Remove first proposition
		driver.findElement(By.xpath("//li[1]//div[1]//div[3]//td[2]//button[1]")).click();

		// Click on clone button
		driver.findElement(By.id("submit-clone-question")).click();

		// Wait for alert to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Ok')]")));

		// Wait
		Thread.sleep(1000);

		// Click on ok button
		driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
