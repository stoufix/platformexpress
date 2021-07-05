package gerer_les_QCM;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Generer_champs_vide_echec {

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
	public void GenerateQuizEmptyField() throws InterruptedException {

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
		// Wait
		Thread.sleep(1000);

		/********* Generate quiz empty field *********/
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();

		// Wait for generate quiz button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("generate-quiz-btn")));
		// Wait
		Thread.sleep(1000);

		// Click on generate quiz button
		driver.findElement(By.id("generate-quiz-btn")).click();

		// Wait for generate quiz modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNumberModal")));
		// Wait
		Thread.sleep(1000);

		// Check submit button is disabled
		boolean buttonEnable = driver.findElement(By.id("submit-generate-quiz")).isEnabled();
		assertFalse(buttonEnable);

		// Write number of question
		driver.findElement(By.id("number-generate")).sendKeys("5");
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean buttonEnableNumber = driver.findElement(By.id("submit-generate-quiz")).isEnabled();
		assertFalse(buttonEnableNumber);
		// Wait
		Thread.sleep(1000);

		// Clear number field
		WebElement titleField = driver.findElement(By.id("number-generate"));
		titleField.sendKeys((Keys.chord(Keys.CONTROL, "a")));
		titleField.sendKeys(Keys.BACK_SPACE);
		titleField.clear();
		Thread.sleep(1000);

		// Write number of question
		driver.findElement(By.id("mark-generate")).sendKeys("2");
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean buttonEnableMark = driver.findElement(By.id("submit-generate-quiz")).isEnabled();
		assertFalse(buttonEnableMark);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
