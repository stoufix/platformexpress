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

public class Supp_utilisateur_echec {

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

		/*********
		 * Navigation To QCM management to get fullName of user that have create a QCM
		 *********/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait for user menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quizzes-management")));
		// Navigate to users section
		driver.findElement(By.id("quizzes-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for display table
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[7]")));

		/********* get the creator name of QCM in the first line of table *********/
		String creatorFullName = driver.findElement(By.xpath("//tr[4]//td[5]")).getText();
		// Wait
		Thread.sleep(1000);

		/********* Navigation to users management to delete this user *********/
		// Navigate
		driver.findElement(By.id("users-management")).click();
		// Wait for display table
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[9]")));
		// Wait
		Thread.sleep(1000);
		// Insert the creator full name in the search field
		driver.findElement(By.id("search-user")).sendKeys(creatorFullName);
		// Wait
		Thread.sleep(1000);
		// Click on delete button
		driver.findElement(By.xpath("//button[@title='Supprimer cet utilisateur']")).click();
		// Wait for confirmation modal to display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='swal2-popup swal2-modal swal2-show']")));
		// Wait
		Thread.sleep(1000);
		// Click on confirmation button that appear in the modal
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait for second modal to display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='swal2-popup swal2-modal swal2-show']")));
		// Wait
		Thread.sleep(2000);
		// Click on the button "OK" that appear in the second modal
		driver.findElement(By.xpath("//button[contains(text(),'ok')]")).click();
		// Wait
		Thread.sleep(1000);
		// Clear data search-field
		driver.findElement(By.id("search-user")).clear();
		// Wait
		Thread.sleep(1000);
		// Search for the user that we have choose to delete it
		driver.findElement(By.id("search-user")).sendKeys(creatorFullName);
		// Wait
		Thread.sleep(1000);
		// Check that the user has not been deleted and still exist in the users table
		String firstName = driver.findElement(By.xpath("//td[contains(text(),'Moez')]")).getText();
		String lastName = driver.findElement(By.xpath("//td[contains(text(),'Barkia')]")).getText();
		String fullName = firstName.concat(" ").concat(lastName);
		assertEquals(fullName, creatorFullName);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
