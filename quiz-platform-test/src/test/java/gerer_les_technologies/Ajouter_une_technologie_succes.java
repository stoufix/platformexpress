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

public class Ajouter_une_technologie_succes {

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
	public void addTechnology() throws InterruptedException {

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
		// Wait for technology add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-technology-btn")));

		/********* Add technology *********/
		// Click on add button
		driver.findElement(By.id("add-technology-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write name
		driver.findElement(By.id("name-add")).sendKeys("Matlab");
		// Wait
		Thread.sleep(1000);
		// Write description
		driver.findElement(By.id("description-add")).sendKeys("Développement sur Matlab");
		// Wait
		Thread.sleep(1000);
		// click on add button
		driver.findElement(By.id("submit-add-technology")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the adding *********/
		// Wait for technology search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));
		// Wait
		Thread.sleep(1000);
		// Write search term with the name of the new technology
		driver.findElement(By.id("search-technology")).sendKeys("Matlab");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first technology of the search result
		String nameTechnology = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
		// Wait
		Thread.sleep(1000);
		// Compare it with the expected name
		assertEquals(nameTechnology, "Matlab");

		// Get the description of first technology of the search result
		String descTechnology = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();
		// Wait
		Thread.sleep(1000);
		// Compare it with the expected name
		assertEquals(descTechnology, "Développement sur Matlab");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
