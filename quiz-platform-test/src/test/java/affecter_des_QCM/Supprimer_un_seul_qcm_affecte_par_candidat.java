package affecter_des_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supprimer_un_seul_qcm_affecte_par_candidat {

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
	public void deleteAssignedQuizByCandidate() throws InterruptedException {

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
		// Click on the name of candidates
		driver.findElement(By.xpath("//a[contains(text(),'Chaima Zamni')]")).click();
		// Wait
		Thread.sleep(1000);
		// Click on delete button to delete the assigned quiz
		driver.findElement(By.xpath("//div[@id='id1']//button[@title='Supprimer ce QCM de la liste de candidat']"))
				.click();
		// Wait
		Thread.sleep(2000);
		// Wait until confirmation modal display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@id='id1']//button[@title='Supprimer ce QCM de la liste de candidat']")));
		// Click "Oui" button to confirm delete action
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
