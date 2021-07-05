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

public class Modif_prenom_carac_speciaux_echec {

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
	public void updateConnectedUserFirstName() throws InterruptedException {

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

		/********* Update User firstName with a special character *********/
		// Clear firstName field
		driver.findElement(By.id("firstName")).clear();
		// Wait
		Thread.sleep(1000);
		// Insert a firstName that contain a special character in the first position
		driver.findElement(By.id("firstName")).sendKeys("_Maha");
		// Wait
		Thread.sleep(1000);
		// Check save button is enable
		boolean condition1 = driver.findElement(By.id("save-profile-info")).isEnabled();
		assertFalse(condition1);
		// Click save button
		driver.findElement(By.id("save-profile-info")).click();
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
