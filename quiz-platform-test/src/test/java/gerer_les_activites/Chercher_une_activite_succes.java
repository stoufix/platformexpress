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

public class Chercher_une_activite_succes {

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
	public void searchActivity() throws InterruptedException {

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
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));

		/********* Search activity with result *********/
		// Write search term
		driver.findElement(By.id("search-activity")).sendKeys("Qualité");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);
		// Get the name of first activity of the search result
		String activityTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
		// Compare it with the expected name
		assertEquals(activityTitle, "Qualité");
		// Wait
		Thread.sleep(1000);

		/********* Search activity without result *********/
		// clear search term
		driver.findElement(By.id("search-activity")).clear();
		// Wait
		Thread.sleep(1000);
		// Write search term
		driver.findElement(By.id("search-activity")).sendKeys("moez");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("no-data-available")));

		// Get the message of no data available
		String noData = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
