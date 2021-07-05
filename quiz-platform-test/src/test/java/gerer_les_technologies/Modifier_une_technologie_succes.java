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

public class Modifier_une_technologie_succes {

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
	public void updateTechnology() throws InterruptedException {

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
		
		/********* Search for technology to edit *********/
		// Wait for technology search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));

		// Write search term with the name of the new technology
		driver.findElement(By.id("search-technology")).sendKeys("Matlab");
		// Wait
		Thread.sleep(1000);
		/********* Edit technology *********/
		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for technology update modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editmodal")));

		// Clear name field
		driver.findElement(By.id("name-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Write name
		driver.findElement(By.id("name-update")).sendKeys("Scala");
		// Wait
		Thread.sleep(1000);
		// Clear description field
		driver.findElement(By.id("description-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Write description
		driver.findElement(By.id("description-update")).sendKeys("Développement Scala");
		// Wait
		Thread.sleep(1000);
		// Click on update button
		driver.findElement(By.id("submit-update-technology")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the edit of technology *********/
		// Wait for technology search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));

		// Write search term
		driver.findElement(By.id("search-technology")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-technology")).sendKeys("Scala");
		// Wait
		Thread.sleep(1000);
		// Wait for search result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of the first technology of search result
		String technologyName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(technologyName, "Scala");
		// Wait
		Thread.sleep(1000);
		// Get the description of the first technology of search result
		String technologyDescription = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected name
		assertEquals(technologyDescription, "Développement Scala");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
