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

public class Supp_une_technologie_echec {
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
	public void deleteTechnologyFail() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		/********* find used technology in question list *********/
		// Wait for technology menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("questions-management")));
		// Navigate to technologies section
		driver.findElement(By.id("questions-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for first question to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Get the technology of the first question
		String questionTechnlogy = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();
		// Wait
		Thread.sleep(1000);

		/********* show technology list *********/
		// Navigate to technologies section
		driver.findElement(By.id("technologies-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* Search for technology to delete *********/
		// Wait for technology search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));

		// Write search term with the name of technology found in the question list
		driver.findElement(By.id("search-technology")).sendKeys(questionTechnlogy);
		// Wait
		Thread.sleep(1000);

		/********* delete technology *********/
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[3]//button[2]//i[1]")));
		// Click on delete button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[2]//i[1]")).click();
		// Wait
		Thread.sleep(1000);
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));
		// click on confirm button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait alert to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Ok')]")));
		// Click on ok button
		driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the deleting of technology *********/
		// Wait for technology search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-technology")));

		// Write search term
		driver.findElement(By.id("search-technology")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-technology")).sendKeys(questionTechnlogy);
		// Wait
		Thread.sleep(1000);

		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first technology of the search result
		String technologyTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(technologyTitle, questionTechnlogy);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
