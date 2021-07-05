package gerer_les_questions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Pagination_de_la_liste_des_questions_succes {

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
	public void searchCandidate() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for candidate menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("questions-management")));

		// Navigate to candidates section
		driver.findElement(By.id("questions-management")).click();

		// Wait for pagination buttons to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'1')]")));

		/********* Pagination actions *********/
		// Click on next if enable
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Suivante')]")).isEnabled();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Suivante')]")).click();
		}

		// Click on previous if enable
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Précédente')]")).isEnabled();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Précédente')]")).click();
		}

		// Wait for pagination buttons to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-items-list-btn")));

		// Click on pagination button to show items list
		driver.findElement(By.id("show-items-list-btn")).click();

		// Choose the number of elements to display by page and click on it
		driver.findElement(By.id("ten-items")).click();
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
