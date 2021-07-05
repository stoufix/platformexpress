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

public class Modifier_une_activite_succes {

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
	public void editActivity() throws InterruptedException {

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
		
		/********* Search for activity to edit *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));

		// Write search term with the name of the new activity
		driver.findElement(By.id("search-activity")).sendKeys("Qualité");
		// Wait
		Thread.sleep(1000);
		
		/********* Edit activity *********/
		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for activity update modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editmodal")));

		// Clear name field
		driver.findElement(By.id("name-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Write name
		driver.findElement(By.id("name-update")).sendKeys("Innovation");
		// Wait
		Thread.sleep(1000);
		// Clear description field
		driver.findElement(By.id("description-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Write description
		driver.findElement(By.id("description-update")).sendKeys("Département Innovation");
		// Wait
		Thread.sleep(1000);
		// Click on update button
		driver.findElement(By.id("submit-update-activity")).click();
		// Wait
		Thread.sleep(2000);
		
		/********* Verify the edit of activity *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));

		// Write search term
		driver.findElement(By.id("search-activity")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-activity")).sendKeys("Innovation");
		// Wait
		Thread.sleep(1000);
		// Wait for search result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of the first activity of search result
		String activityName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(activityName, "Innovation");

		// Get the description of the first activity of search result
		String activityDescription = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected name
		assertEquals(activityDescription, "Département Innovation");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
