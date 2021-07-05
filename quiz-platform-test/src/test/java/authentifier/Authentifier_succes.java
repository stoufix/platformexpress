package authentifier;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Authentifier_succes {
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
	public void Login() throws InterruptedException {

		// Wait
		Thread.sleep(1000);

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Wait
		Thread.sleep(1000);
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Wait
		Thread.sleep(1000);
		// Click on login button
		driver.findElement(By.id("submit")).click();
		// Wait
		Thread.sleep(1000);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait
		Thread.sleep(1000);
		// Wait for first page to display
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Tableau de bord')]")));
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
