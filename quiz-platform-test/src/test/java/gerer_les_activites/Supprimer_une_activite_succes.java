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

public class Supprimer_une_activite_succes {

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
	public void deleteActivity() throws InterruptedException {

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

		/********* search for activity to delete *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));

		// Write search term
		driver.findElement(By.id("search-activity")).sendKeys("Innovation");
		// Wait
		Thread.sleep(1000);

		/********* delete activity *********/
		// Get the name of the activity to delete
		String activityName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Get the description of the activity to delete
		String activityDescription = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
		// Wait
		Thread.sleep(1000);

		// Click on delete button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[2]//i[1]")).click();

		// Wait for delete modal to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));

		// click on delete button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the deleting of activity *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));
		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-activity")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-activity")).sendKeys(activityName);
		// Wait
		Thread.sleep(1000);

		// Get the message of no data available
		String noData = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");
		// Wait
		Thread.sleep(1000);
		// Write search term
		driver.findElement(By.id("search-activity")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-activity")).sendKeys(activityDescription);
		// Wait
		Thread.sleep(1000);
		// Get the message of no data available
		String noDatamsg = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noDatamsg, "Aucune donnée trouvée.");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
