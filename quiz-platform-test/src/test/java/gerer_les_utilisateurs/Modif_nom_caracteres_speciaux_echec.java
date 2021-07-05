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

public class Modif_nom_caracteres_speciaux_echec {

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
	public void updateUserLastNameSpecialCharacters() throws InterruptedException {

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

		/********* Update user lastName *********/
		// Wait for user update button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[9]//button[1]")));
		driver.findElement(By.xpath("//tr[1]//td[9]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for display modal to update user
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-modal")));
		// Clear data form field lastName
		driver.findElement(By.id("lastName-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Fill data form field lastName using special characters in the first letter
		driver.findElement(By.id("lastName-update")).sendKeys("$Soussi");
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean condition = driver.findElement(By.id("submit-update-user")).isEnabled();
		assertEquals(condition, false);
		// Click on submit button
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
