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

public class Supp_une_activite_echec {
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
	public void deleteActivityFail() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		/********* find used activity in question list *********/
		// Wait for activity menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("questions-management")));
		// Navigate to activities section
		driver.findElement(By.id("questions-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for first question to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Get the activity of the first question
		String questionActivity = driver.findElement(By.xpath("//tr[1]//td[6]")).getText();
		// Wait
		Thread.sleep(1000);
		
		/********* show activity list *********/
		// Navigate to activities section
		driver.findElement(By.id("activities-management")).click();
		// Wait
		Thread.sleep(1000);
		
		/********* Search for activity to delete *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));

		// Write search term with the name of activity found in the question list
		driver.findElement(By.id("search-activity")).sendKeys(questionActivity);
		// Wait
		Thread.sleep(1000);
		
		/********* delete activity *********/
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

		/********* Verify the deleting of activity *********/
		// Wait for activity search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-activity")));

		// Write search term
		driver.findElement(By.id("search-activity")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-activity")).sendKeys(questionActivity);
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first activity of the search result
		String activityTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
		// Compare it with the expected name
		assertEquals(activityTitle, questionActivity);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
