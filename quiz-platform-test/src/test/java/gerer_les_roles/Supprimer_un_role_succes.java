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

public class Supprimer_un_role_succes {
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
	public void deleteRole() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for degree menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roles-management")));

		// Navigate to degrees section
		driver.findElement(By.id("roles-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* search for degree to delete *********/
		// Wait for degree search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		// Write search term
		driver.findElement(By.id("search-role")).sendKeys("Développeur");
		// Wait
		Thread.sleep(1000);

		/********* delete degree *********/

		// Get the description of the role to delete
		String roleDescription = driver.findElement(By.xpath("//body//td[2]")).getText();

		// Click on delete button
		driver.findElement(By.xpath("//button[@title='Supprimer ce rôle']")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for delete modal to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));

		// click on delete button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the deleting of degree *********/
		// Wait for degree search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		// Write search term
		driver.findElement(By.id("search-role")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-role")).sendKeys(roleDescription);
		// Wait
		Thread.sleep(1000);

		// Get the message of no data available
		String noDatamsg = driver.findElement(By.id("no-data-available")).getText();

		// Compare no data message with the expected message
		assertEquals(noDatamsg, "Aucune donnée trouvée.");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
