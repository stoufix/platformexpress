package affecter_des_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supprimer_tous_le_qcm_affectes_par_candidat {

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
	public void deleteAllAssignedQuizzesByCandidate() throws InterruptedException {

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
		// Wait until Assign Quizzes button display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("assign-quizzes-to-candidates-btn")));
		// Click on delete button
		driver.findElement(By.xpath("//tr[1]//td[1]//div[1]//div[5]//button[2]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait until confirmation modal display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='swal2-popup swal2-modal swal2-show']")));
		// Click on "Oui" button to confirm delete action
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(1000);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
