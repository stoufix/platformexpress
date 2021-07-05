package gerer_les_roles;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chercher_un_role_succes {

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
	public void searchRole() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for role menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roles-management")));

		// Navigate to roles section
		driver.findElement(By.id("roles-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for role search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		/********* Search role with result *********/
		// Write search term
		driver.findElement(By.id("search-role")).sendKeys("Ingénieur");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first role of the search result
		String roleName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(roleName, "Ingénieur");
		// Wait
		Thread.sleep(1000);

		/********* Search role without result *********/
		// clear search term
		driver.findElement(By.id("search-role")).clear();
		// Wait
		Thread.sleep(1000);
		// Write search term
		driver.findElement(By.id("search-role")).sendKeys("Employé");
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
