package gerer_les_utilisateurs;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supprimer_un_utilisateur_succes {

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
	public void deleteUser() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		/********* Navigation *********/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait for user menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("users-management")));
		// Navigate to users section
		driver.findElement(By.id("users-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for display search field
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-user")));

		/********* Delete user *********/
		driver.findElement(By.id("search-user")).sendKeys("ghassen.soussi.contact@gmail.com");
		// Wait
		Thread.sleep(1000);
		// Wait for display delete button
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[9]//button[2]")));
		// Click delete button
		driver.findElement(By.xpath("//tr[1]//td[9]//button[2]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for display confirmation modal
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));
		// Wait
		Thread.sleep(1000);
		// click confirm button to delete user
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(2000);

		/********* Check if the user has been deleted *********/
		// clear data search user field
		driver.findElement(By.id("search-user")).clear();
		// Wait
		Thread.sleep(1000);
		// insert the mail of the user that you have delete it
		driver.findElement(By.id("search-user")).sendKeys("ghassen.soussi.contact@gmail.com");
		// Wait
		Thread.sleep(1000);
		String emptyDataMessage = driver.findElement(By.id("no-data-available")).getText();
		assertEquals(emptyDataMessage, "Aucune donnée trouvée.");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
