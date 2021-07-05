package gerer_les_niveaux;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modifier_un_niveau_succes {

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
	public void updateDegree() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for degree menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("degrees-management")));

		// Navigate to degrees section
		driver.findElement(By.id("degrees-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* Search for degree to edit *********/
		// Wait for degree search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-degree")));

		// Write search term with the name of the new degree
		driver.findElement(By.id("search-degree")).sendKeys("Confirmé");
		// Wait
		Thread.sleep(1000);

		/********* Edit degree *********/
		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for degree update modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editmodal")));

		// Clear name field
		driver.findElement(By.id("name-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Write name
		driver.findElement(By.id("name-update")).sendKeys("Confirmé");
		// Wait
		Thread.sleep(1000);
		// Clear description field
		driver.findElement(By.id("description-update")).clear();
		// Wait
		Thread.sleep(1000);
		// Write description
		driver.findElement(By.id("description-update")).sendKeys("3 à 4 ans d’expérience");
		// Wait
		Thread.sleep(1000);
		// Click on update button
		driver.findElement(By.id("submit-update-degree")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the edit of degree *********/
		// Wait for degree search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-degree")));

		// Write search term
		driver.findElement(By.id("search-degree")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-degree")).sendKeys("Confirmé");
		// Wait
		Thread.sleep(1000);

		// Wait for search result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of the first degree of search result
		String degreeName = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(degreeName, "Confirmé");
		// Wait
		Thread.sleep(1000);

		// Get the description of the first degree of search result
		String degreeDescription = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected name
		assertEquals(degreeDescription, "3 à 4 ans d’expérience");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
