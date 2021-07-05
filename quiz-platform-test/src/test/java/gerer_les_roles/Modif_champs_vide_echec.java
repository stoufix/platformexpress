package gerer_les_roles;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modif_champs_vide_echec {

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
	public void updateroleEmptyFields() throws InterruptedException {

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

		// Wait for role add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-role-btn")));

		/********* Search for role to edit *********/
		// Wait for role search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		// Write search term with the name of the new role
		driver.findElement(By.id("search-role")).sendKeys("Ingénieur");
		// Wait
		Thread.sleep(1000);

		/********* Edit role *********/
		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[3]//button[1]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for role update modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editmodal")));

		// Clear name field
		WebElement field = driver.findElement(By.id("name-update"));
		field.sendKeys((Keys.chord(Keys.CONTROL, "a")));
		field.sendKeys(Keys.BACK_SPACE);
		field.clear();
		// Wait
		Thread.sleep(1000);

		// Check submit button is disabled
		boolean buttonEnable = driver.findElement(By.id("submit-update-role")).isEnabled();
		assertFalse(buttonEnable);
		// Wait
		Thread.sleep(1000);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
