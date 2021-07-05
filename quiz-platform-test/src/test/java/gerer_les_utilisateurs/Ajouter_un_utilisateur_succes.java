package gerer_les_utilisateurs;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajouter_un_utilisateur_succes {

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
	public void addUser() throws InterruptedException {

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
		// Wait for user add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-user-btn")));

		/********* Add user *********/
		// Click on add button
		driver.findElement(By.id("add-user-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-modal")));
		// Write first name
		driver.findElement(By.id("firstName-add")).sendKeys("Ghassen");
		// Wait
		Thread.sleep(1000);
		// Write last name
		driver.findElement(By.id("lastName-add")).sendKeys("Soussi");
		// Wait
		Thread.sleep(1000);
		// Write email
		driver.findElement(By.id("email-add")).sendKeys("ghassen.soussi.contact@gmail.com");
		// Wait
		Thread.sleep(1000);
		// Write phone number
		driver.findElement(By.id("phoneNumber-add")).sendKeys("97291373");
		// Wait
		Thread.sleep(1000);
		// Write address
		driver.findElement(By.id("address-add")).sendKeys("Tunis - Cité Khadra");
		// Wait
		Thread.sleep(1000);
		// Write birthDate
		driver.findElement(By.id("birthday-add")).sendKeys("26/07/1994");
		// Wait
		Thread.sleep(1000);
		// Select role
		Select role = new Select(driver.findElement(By.id("role-add")));
		role.selectByVisibleText("Manager");
		// Wait
		Thread.sleep(1000);
		// Select activity
		Select activity = new Select(driver.findElement(By.id("activity-add")));
		activity.selectByVisibleText("Sécurité");
		// Wait
		Thread.sleep(1000);
		// Check submit button is enable
		boolean condition9 = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition9, true);
		// Click on submit button
		driver.findElement(By.id("submit-add-user")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the adding *********/
		// Wait for table to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-user")));
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-user")).sendKeys("ghassen.soussi.contact@gmail.com");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Check if data field entry is valid
		String firstName = driver.findElement(By.xpath("//td[contains(text(),'Ghassen')]")).getText();
		assertEquals(firstName, "Ghassen");
		// Check if data field entry is valid
		String lastName = driver.findElement(By.xpath("//td[contains(text(),'Soussi')]")).getText();
		assertEquals(lastName, "Soussi");
		// Check if data field entry is valid
		String email = driver.findElement(By.xpath("//span[@class='mail-link']")).getText();
		assertEquals(email, "ghassen.soussi.contact@gmail.com");
		// Check if data field entry is valid
		String phoneNumber = driver.findElement(By.xpath("//td[contains(text(),'97291373')]")).getText();
		assertEquals(phoneNumber, "97291373");
		// Check if data field entry is valid
		String address = driver.findElement(By.xpath("//td[contains(text(),'Tunis - Cité Khadra')]")).getText();
		assertEquals(address, "Tunis - Cité Khadra");
		// Check if data field entry is valid
		String roleChosen = driver.findElement(By.xpath("//td[contains(text(),'Manager')]")).getText();
		assertEquals(roleChosen, "Manager");
		// Check if data field entry is valid
		String activityChosen = driver.findElement(By.xpath("//td[contains(text(),'Sécurité')]")).getText();
		assertEquals(activityChosen, "Sécurité");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
