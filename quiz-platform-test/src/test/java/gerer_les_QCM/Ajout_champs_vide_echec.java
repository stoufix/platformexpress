package gerer_les_QCM;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajout_champs_vide_echec {

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
	public void addQuizEmptyFields() throws InterruptedException {

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

		// Wait for quiz add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-quiz-btn")));
		// Wait
		Thread.sleep(1000);

		/********* Add quiz Empty fields *********/
		// Click on add button
		driver.findElement(By.id("add-quiz-btn")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Check button
		boolean buttonEnable = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable);
		// Wait
		Thread.sleep(1000);

		// Write title
		driver.findElement(By.id("title-add")).sendKeys("Nom de QCM");
		// Check button
		boolean buttonEnableTitle = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnableTitle);
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.id("description-add")).sendKeys("Description de QCM");
		// Check button
		boolean buttonEnableDesc = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnableDesc);
		// Wait
		Thread.sleep(1000);

		// Write duration
		driver.findElement(By.id("duration-add")).sendKeys("20");
		// Check button
		boolean buttonEnableDuration = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnableDuration);
		// Wait
		Thread.sleep(1000);

		// Select degree
		Select degree = new Select(driver.findElement(By.id("degree-add")));
		degree.selectByVisibleText("Débutant");
		// Check button
		boolean buttonEnableDegree = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertTrue(buttonEnableDegree);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
