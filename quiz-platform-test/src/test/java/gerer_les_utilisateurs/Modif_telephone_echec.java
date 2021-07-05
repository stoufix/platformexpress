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

public class Modif_telephone_echec {

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
	public void updatePhoneNumber() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		/********* Navigation *********/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait for user side-bar to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("users-management")));
		// Navigate to users management section
		driver.findElement(By.id("users-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* Update User phoneNumber *********/
		// Search for user to update
		driver.findElement(By.id("search-user")).sendKeys("ghassen.soussi.contact@gmail.com");
		// Wait
		Thread.sleep(1000);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[9]//button[1]")));
		driver.findElement(By.xpath("//tr[1]//td[9]//button[1]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for display modal to update user
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-modal")));
		// Clear data form field address
		driver.findElement(By.id("phoneNumber-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Fill phoneNumber field with a wrong format example letters and numbers
		driver.findElement(By.id("phoneNumber-update")).sendKeys("gh97291373");
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean condition = driver.findElement(By.id("submit-update-user")).isEnabled();
		assertEquals(condition, false);
		// Click submit button
		driver.findElement(By.id("submit-update-user")).click();
		// Wait
		Thread.sleep(1000);
		// Clear phoneNumber field
		driver.findElement(By.id("phoneNumber-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Fill the phoneNumber field with a wrong format example exceed 8 numbers
		driver.findElement(By.id("phoneNumber-update")).sendKeys("97291373000");
		// Check submit button is disabled
		boolean condition2 = driver.findElement(By.id("submit-update-user")).isEnabled();
		assertEquals(condition2, false);
		// Click submit button
		driver.findElement(By.id("submit-update-user")).click();
		// Wait
		Thread.sleep(1000);
		// Clear phoneNumber field
		driver.findElement(By.id("phoneNumber-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Fill the phoneNumber field with a wrong format example inferior 8 numbers
		driver.findElement(By.id("phoneNumber-update")).sendKeys("97291");
		// Check submit button is disabled
		boolean condition3 = driver.findElement(By.id("submit-update-user")).isEnabled();
		assertEquals(condition3, false);
		// Click submit button
		driver.findElement(By.id("submit-update-user")).click();
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
