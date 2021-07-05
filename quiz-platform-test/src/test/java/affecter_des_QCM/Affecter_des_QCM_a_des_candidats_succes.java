package affecter_des_QCM;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Affecter_des_QCM_a_des_candidats_succes {

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
	public void assignQuizzes() throws InterruptedException {

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

		/********* Navigation to assign-quizzes-management *********/
		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
		// Wait for user menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("assign-quizzes")));
		// Navigate to users section
		driver.findElement(By.id("assign-quizzes")).click();
		// Wait
		Thread.sleep(1000);
		// Wait until assign quizzes button display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("assign-quizzes-to-candidates-btn")));

		/********* Navigation to assign-quizzes-New *********/
		// Click on button Assign Quizzes
		driver.findElement(By.id("assign-quizzes-to-candidates-btn")).click();
		// Wait
		Thread.sleep(1000);
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-assign-quizzes-to-candidates-btn")));

		/********* Select candidates *********/
		driver.findElement(By.xpath("//tr[1]//td[3]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean condition = driver.findElement(By.id("save-assign-quizzes-to-candidates-btn")).isEnabled();
		assertFalse(condition);
		// Wait
		Thread.sleep(1000);

		/********* Select quizzes *********/
		driver.findElement(By.xpath("//tr[1]//td[4]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Check submit button is enable
		boolean condition2 = driver.findElement(By.id("save-assign-quizzes-to-candidates-btn")).isEnabled();
		assertTrue(condition2);
		// Wait
		Thread.sleep(1000);

		/********* Select date *********/
		driver.findElement(By.id("date")).sendKeys("19/03/2019");
		// Wait
		Thread.sleep(1000);
		// Click on save button
		driver.findElement(By.id("save-assign-quizzes-to-candidates-btn")).click();
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
