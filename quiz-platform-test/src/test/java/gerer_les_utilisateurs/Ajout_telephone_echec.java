package gerer_les_utilisateurs;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajout_telephone_echec {

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
	public void insertPhoneNumber() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		;
		// Click on login button
		driver.findElement(By.id("submit")).click();

		/********* Navigation *********/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait for user menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("users-management")));
		// Navigate to users section
		driver.findElement(By.id("users-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for user add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-user-btn")));

		/********* Insert user phoneNumber *********/
		// Click on add button
		driver.findElement(By.id("add-user-btn")).click();
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-modal")));
		// Fill the phoneNumber field with a wrong format example letters and numbers
		driver.findElement(By.id("phoneNumber-add")).sendKeys("gh291373");
		// Wait
		Thread.sleep(1000);
		
		// Check submit button is disabled
		boolean condition = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition, false);
		// Wait
		Thread.sleep(1000);
		
		// clear phoneNumber field
		driver.findElement(By.id("phoneNumber-add")).clear();
		// Wait
		Thread.sleep(1000);

		// Fill the phoneNumber field with a wrong format example exceed 8 numbers
		driver.findElement(By.id("phoneNumber-add")).sendKeys("97291373000");
		// Wait
		Thread.sleep(1000);

		// Check submit button is disabled
		boolean condition2 = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition2, false);

		// Fill the phoneNumber field with a wrong format example inferior 8 numbers
		driver.findElement(By.id("phoneNumber-add")).sendKeys("97291");
		// Wait
		Thread.sleep(1000);

		// Check submit button is disabled
		boolean condition3 = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition3, false);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
