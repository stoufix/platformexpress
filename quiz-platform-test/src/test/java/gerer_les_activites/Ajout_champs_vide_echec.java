package gerer_les_activites;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
	public void addActivityEmptyFields() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for activity menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("activities-management")));
		
		// Navigate to activities section
		driver.findElement(By.id("activities-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for activity add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-activity-btn")));

		/********* Add activity Empty fields *********/
		// click on add button
		driver.findElement(By.id("add-activity-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// click on add button
		boolean buttonEnable = driver.findElement(By.id("submit-add-activity")).isEnabled();
		assertEquals(buttonEnable, false);
		// Wait
		Thread.sleep(1000);
		// Leave the field "Name" empty
		// Write description
		driver.findElement(By.id("description-add")).sendKeys("Description activité");

		// Check submit button is enable
		assertEquals(buttonEnable, false);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
