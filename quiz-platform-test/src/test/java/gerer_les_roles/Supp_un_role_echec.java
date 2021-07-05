package gerer_les_roles;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supp_un_role_echec {
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
	public void deleteRoleFail() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		/********* find used degree in question list *********/
		// Wait for user menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("users-management")));
		// Navigate to users section
		driver.findElement(By.id("users-management")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for the first element to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Get the name of the first element role
		String userRole = driver.findElement(By.xpath("//tr[1]//td[6]")).getText();
		// Wait
		Thread.sleep(1000);

		/********* show role list *********/
		// Navigate to roles section
		driver.findElement(By.id("roles-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* Search for role to delete *********/
		// Wait for role search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		// Write search term with the name of degree found in the question list
		driver.findElement(By.id("search-role")).sendKeys(userRole);
		// Wait
		Thread.sleep(1000);

		/********* delete role *********/

		// Click on delete button
		driver.findElement(By.xpath("//button[@title='Supprimer ce rôle']")).click();
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

		/********* Verify the deleting of role *********/
		// Wait for role search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		// Write search term
		driver.findElement(By.id("search-role")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-role")).sendKeys(userRole);
		// Wait
		Thread.sleep(1000);

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'Admin')]")));

		// Get the name of first role of the search result
		String roleTitle = driver.findElement(By.xpath("//td[contains(text(),'Admin')]")).getText();

		// Compare it with the expected name
		assertEquals(roleTitle, userRole);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
