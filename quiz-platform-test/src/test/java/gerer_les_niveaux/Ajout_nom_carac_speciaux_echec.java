package gerer_les_niveaux;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajout_nom_carac_speciaux_echec {
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
	public void addDegreeNameSpecialCharacters() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for user menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("degrees-management")));

		// Navigate to users section
		driver.findElement(By.id("degrees-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for user add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-degree-btn")));

		/********* Add degree with special characters in name field *********/
		// Click on add button
		driver.findElement(By.id("add-degree-btn")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write name with special characters
		driver.findElement(By.id("name-add")).sendKeys("$Confirmé");
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.id("description-add")).sendKeys("Niveau Confirmé");
		// Wait
		Thread.sleep(1000);

		// Check submit button is disabled
		boolean buttonEnable = driver.findElement(By.id("submit-add-degree")).isEnabled();
		assertEquals(buttonEnable, false);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("name-add")).sendKeys("!Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("name-add")).sendKeys("?Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);
		// Wait
		Thread.sleep(1000);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		driver.findElement(By.id("name-add")).sendKeys(";Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		driver.findElement(By.id("name-add")).sendKeys("+Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		driver.findElement(By.id("name-add")).sendKeys("-Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		driver.findElement(By.id("name-add")).sendKeys("<Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		driver.findElement(By.id("name-add")).sendKeys(">Confirmé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);

		// Write name with special characters
		driver.findElement(By.id("name-add")).clear();
		driver.findElement(By.id("name-add")).sendKeys("*Confimé");
		// Check submit button is disabled
		assertEquals(buttonEnable, false);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
