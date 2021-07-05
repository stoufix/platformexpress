package modifier_le_profil;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modif_profil_telephone_echec {

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
	public void updateConnectedUserLastName() throws InterruptedException {

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

		/********* Update User phoneNumber with a wrong format *********/
		// Clear Email field
		driver.findElement(By.id("phoneNumber")).clear();
		// Wait
		Thread.sleep(1000);
		// Insert phoneNumber that does not exceeds 7 numbers
		driver.findElement(By.id("phoneNumber")).sendKeys("50988");
		// Wait
		Thread.sleep(1000);
		// Check save button is disabled
		boolean condition1 = driver.findElement(By.id("save-profile-info")).isEnabled();
		assertFalse(condition1);
		// Click save button
		driver.findElement(By.id("save-profile-info")).click();
		// Clear phoneNumber field
		driver.findElement(By.id("phoneNumber")).clear();
		// Wait
		Thread.sleep(1000);
		// Insert phoneNumber that exceeds 8 numbers
		driver.findElement(By.id("phoneNumber")).sendKeys("50988325000");
		// Wait
		Thread.sleep(1000);
		// Check save button is disabled
		boolean condition2 = driver.findElement(By.id("save-profile-info")).isEnabled();
		assertFalse(condition2);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
