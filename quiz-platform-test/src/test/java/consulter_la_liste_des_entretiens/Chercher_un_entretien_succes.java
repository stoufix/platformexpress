package consulter_la_liste_des_entretiens;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Chercher_un_entretien_succes {

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
	public void searchInterview() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for interview menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("list-interviews")));

		// Navigate to interview section
		driver.findElement(By.id("list-interviews")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for interview search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-interview")));

		/********* Search interview with result *********/
		// Write search term
		driver.findElement(By.id("search-interview")).sendKeys("sana");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'sana mahjoub')]")));

		// Get the name of first interview of the search result
		String candidateName = driver.findElement(By.xpath("//td[contains(text(),'sana mahjoub')]")).getText();

		// Compare it with the expected name
		assertEquals(candidateName, "sana mahjoub");
		// Wait
		Thread.sleep(1000);

		/********* Search interview without result *********/
		// clear search term
		driver.findElement(By.id("search-interview")).clear();
		// Wait
		Thread.sleep(1000);
		// Write search term
		driver.findElement(By.id("search-interview")).sendKeys("Consultant");
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
