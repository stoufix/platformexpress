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

public class Ajouter_une_activite_succes {
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
	public void addActivity() throws InterruptedException {

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

		/********* Add activity *********/
		// Click on add button
		driver.findElement(By.id("add-activity-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write name
		driver.findElement(By.id("name-add")).sendKeys("Qualité");
		// Wait
		Thread.sleep(1000);
		// Write description
		driver.findElement(By.id("description-add")).sendKeys("Département Qualité");
		// Wait
		Thread.sleep(1000);
		// click on add button
		driver.findElement(By.id("submit-add-activity")).click();
		// Wait
		Thread.sleep(2000);
		
		/********* Verify the adding *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));
		// Wait
		Thread.sleep(1000);
		// Write search term with the name of the new activity
		driver.findElement(By.id("search-activity")).sendKeys("Qualité");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first activity of the search result
		String nameActivity = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(nameActivity, "Qualité");

		// Get the description of first activity of the search result
		String descActivity = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected name
		assertEquals(descActivity, "Département Qualité");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
