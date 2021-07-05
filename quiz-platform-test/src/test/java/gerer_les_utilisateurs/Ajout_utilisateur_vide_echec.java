package gerer_les_utilisateurs;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajout_utilisateur_vide_echec {

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
	public void addUserEmptyField() throws InterruptedException {

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

		/********* Add user without inserting the firstName *********/
		// Click on add button
		driver.findElement(By.id("add-user-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-modal")));
		driver.findElement(By.xpath("//*[@id=\"submit-add-user\"]")).click();
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
		// Check submit button is disabled
		boolean condition = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition, false);
		// Wait
		Thread.sleep(1000);

		/********* Add user without inserting the email *********/
		// Write firstName
		driver.findElement(By.id("firstName-add")).sendKeys("Ghassen");
		// Wait
		Thread.sleep(1000);
		// Clear email field
		WebElement field = driver.findElement(By.id("email-add"));
		field.sendKeys((Keys.chord(Keys.CONTROL, "GhassenSoussi")));
		field.sendKeys(Keys.BACK_SPACE);
		field.clear();
		// Check submit button is disabled
		boolean condition2 = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition2, false);
		// Wait
		Thread.sleep(1000);

		/********* Add user without choosing a phoneNumber *********/
		// Fill email field
		driver.findElement(By.id("email-add")).sendKeys("ghassen.soussi.contact@gmail.com");
		// Wait
		Thread.sleep(1000);
		// Clear phoneNumber field
		WebElement fieldPhoneNumber = driver.findElement(By.id("phoneNumber-add"));
		fieldPhoneNumber.sendKeys((Keys.chord(Keys.CONTROL, "GhassenSoussi")));
		fieldPhoneNumber.sendKeys(Keys.BACK_SPACE);
		fieldPhoneNumber.clear();
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean condition3 = driver.findElement(By.id("submit-add-user")).isEnabled();
		assertEquals(condition3, false);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
