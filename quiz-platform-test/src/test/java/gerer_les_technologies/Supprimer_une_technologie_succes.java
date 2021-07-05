package gerer_les_technologies;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supprimer_une_technologie_succes {

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
	public void deleteTechnology() throws InterruptedException {

		/********* Login *********/

		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");

		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");

		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for technology menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("technologies-management")));

		// Navigate to technologies section
		driver.findElement(By.id("technologies-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* search for technology to delete *********/
		// Wait for degree search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));

		// Write search term
		driver.findElement(By.id("search-technology")).sendKeys("Scala");
		// Wait
		Thread.sleep(1000);

		/********* delete technology *********/
		// Get the name of the technology to delete
		String technologyName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Get the description of the technology to delete
		String technologyDescription = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Click on delete button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[2]//i[1]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for delete modal to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));

		// click on delete button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the deleting of technology *********/
		// Wait for technology search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));

		// Write search term
		driver.findElement(By.id("search-technology")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-technology")).sendKeys(technologyName);
		// Wait
		Thread.sleep(1000);
		// Get the message of no data available
		String noData = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noData, "Aucune donnée trouvée.");

		// Write search term
		driver.findElement(By.id("search-technology")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-technology")).sendKeys(technologyDescription);
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
