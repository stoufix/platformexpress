package gerer_la_liste_des_messages_des_candidats;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Afficher_un_message_succes {
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
	public void displayMessage() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for reclamation menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("candidates-messages")));

		// Navigate to activities section
		driver.findElement(By.id("candidates-messages")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for reclamation search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-message")));

		/********* Search reclamation with result *********/
		// Write search term
		driver.findElement(By.id("search-message")).sendKeys("sana");
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//table[@class='table table-inbox table-hover']//tbody//tr")));
		driver.findElement(By.xpath("//table[@class='table table-inbox table-hover']//tbody//tr")).click();
		// Wait
		Thread.sleep(3000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
