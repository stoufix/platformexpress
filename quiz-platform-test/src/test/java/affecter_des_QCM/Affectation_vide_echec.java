package affecter_des_QCM;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Affectation_vide_echec {

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
	public void emptyAssignment() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
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

		/********* Remove selected candidates *********/
		driver.findElement(By.cssSelector(
				"div.main:nth-child(2) div.content div.row:nth-child(1) div.col-lg-12 div.card div.card-body div.row div.col-lg-5:nth-child(1) ul.list-group.list-group-flush li:nth-child(1) div.notice.notice-info > button.icon-button.float-right:nth-child(2)"))
				.click();
		// Wait
		Thread.sleep(1000);

		/********* Select quizzes *********/
		driver.findElement(By.xpath("//tr[1]//td[4]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean condition2 = driver.findElement(By.id("save-assign-quizzes-to-candidates-btn")).isEnabled();
		assertFalse(condition2);

		/********* Select date of passage *********/
		driver.findElement(By.id("date")).sendKeys("21/03/2019");
		// Wait
		Thread.sleep(1000);
		// Check submit button is disabled
		boolean condition3 = driver.findElement(By.id("save-assign-quizzes-to-candidates-btn")).isEnabled();
		assertFalse(condition3);
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
