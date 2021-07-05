package gerer_les_QCM;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajout_nom_carac_speciaux_echec {
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
	public void addQuizNameSpecialCharacters() throws InterruptedException {

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

		/********* Add quiz with special characters in title field *********/
		// Click on add button
		driver.findElement(By.id("add-quiz-btn")).click();

		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write name with special characters
		driver.findElement(By.id("title-add")).sendKeys("$QCM Java");
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.id("description-add"))
				.sendKeys("QCM Java niveau débutant pour tester les notions de base de programmation Java");
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.id("duration-add")).sendKeys("30");
		// Wait
		Thread.sleep(1000);

		// Select degree
		Select degree = new Select(driver.findElement(By.id("degree-add")));
		degree.selectByVisibleText("Débutant");
		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("visibility-add")).click();
		// Wait
		Thread.sleep(1000);

		// Check submit button is disabled
		boolean buttonEnable = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys("!QCM Java");
		// Check submit button is disabled
		boolean buttonEnable1 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable1);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys("?QCM Java");
		// Check submit button is disabled
		boolean buttonEnable2 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable2);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys(";QCM Java");
		// Check submit button is disabled
		boolean buttonEnable3 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable3);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys("+QCM Java");
		// Check submit button is disabled
		boolean buttonEnable4 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable4);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys("-QCM Java");
		// Check submit button is disabled
		boolean buttonEnable5 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable5);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys("<QCM Java");
		// Check submit button is disabled
		boolean buttonEnable6 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable6);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys(">QCM Java");
		// Check submit button is disabled
		boolean buttonEnable7 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable7);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("title-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("title-add")).sendKeys("*QCM Java");
		// Check submit button is disabled
		boolean buttonEnable8 = driver.findElement(By.id("submit-add-quiz")).isEnabled();
		assertFalse(buttonEnable8);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
