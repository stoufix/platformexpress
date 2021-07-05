package authentifier;

import static org.testng.Assert.assertTrue;

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

public class Authentifier_champs_vide_echec {
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
	public void loginEmmtyField() throws InterruptedException {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait
		Thread.sleep(1000);

		/********* Login *********/
		// Click on login button
		driver.findElement(By.id("submit")).click();
		// Check if login error message is present
		boolean loginError = driver.findElements(By.xpath("//div[contains(text(),'Saisissez votre login')]"))
				.size() > 0;
		assertTrue(loginError);
		// Wait
		Thread.sleep(1000);
		// Check if password error message is present
		boolean passwordError = driver.findElements(By.xpath("//div[contains(text(),'Saisissez votre mot de passe')]"))
				.size() > 0;
		assertTrue(passwordError);
		// Wait
		Thread.sleep(1000);

		// Write userName
		driver.findElement(By.id("username")).sendKeys("moez");
		Thread.sleep(1000);
		// Click on login button
		driver.findElement(By.id("submit")).click();
		// Check if password error message is present
		boolean passwordErrorWithLogin = driver
				.findElements(By.xpath("//div[contains(text(),'Saisissez votre mot de passe')]")).size() > 0;
		assertTrue(passwordErrorWithLogin);
		Thread.sleep(1000);

		// Clear userName field
		WebElement titleField = driver.findElement(By.id("username"));
		titleField.sendKeys((Keys.chord(Keys.CONTROL, "a")));
		titleField.sendKeys(Keys.BACK_SPACE);
		titleField.clear();
		// Wait
		Thread.sleep(1000);
		// Wait for password to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		Thread.sleep(1000);
		// Click on login button
		driver.findElement(By.id("submit")).click();
		// Check if login error message is present
		boolean loginErrorWithPwd = driver.findElements(By.xpath("//div[contains(text(),'Saisissez votre login')]"))
				.size() > 0;
		assertTrue(loginErrorWithPwd);
		Thread.sleep(1000);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
