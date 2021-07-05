package modifier_le_profil;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modifier_le_profil_succes {

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
	public void updateUser() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		/********* Navigation to my profile *********/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait for drop-down to display
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='nav-link dropdown-toggle']")));
		// Click on drop-down to display
		driver.findElement(By.xpath("//a[@class='nav-link dropdown-toggle']")).click();
		// Wait for my-account option to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("my-account")));
		// Wait
		Thread.sleep(1000);
		// Click on my-account option
		driver.findElement(By.id("my-account")).click();
		// Wait
		Thread.sleep(1000);

		/********* Update User lastName *********/
		// Clear address field
		driver.findElement(By.id("lastName")).clear();
		// Wait
		Thread.sleep(1000);

		/********* Other method to fill field *********/
		// WebElement we = driver.findElement(By.id("lastName"));
		// Actions actionList = new Actions(driver);
		// actionList.clickAndHold(we).sendKeys("BenSaid").release().build().perform();

		/********* End method *********/
		// Fill lastName field
		driver.findElement(By.id("lastName")).sendKeys("BenSaid");
		// Wait
		Thread.sleep(1000);
		// Check save button is enabled
		boolean condition = driver.findElement(By.id("save-profile-info")).isEnabled();
		assertTrue(condition);
		// Click on save button
		driver.findElement(By.id("save-profile-info")).click();
		// Wait until confirmation modal display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='swal2-popup swal2-modal swal2-show']")));
		// Wait
		Thread.sleep(1000);
		// Click on "OK" button
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
		// Wait

		Thread.sleep(1000);
		// Check if the modification has been save with success - get user lastName and
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='firstName']")));
		WebElement ele = driver.findElement(By.id("lastName"));
		assertEquals(ele.getAttribute("value"), "BenSaid");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
