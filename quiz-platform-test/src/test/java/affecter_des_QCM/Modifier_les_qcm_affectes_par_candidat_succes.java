package affecter_des_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modifier_les_qcm_affectes_par_candidat_succes {

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
	public void updateAssignedQuizzesByCandidate() throws InterruptedException {

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
		// Wait until update assigned Quizzes display in the table
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]//div[1]//div[5]//button[1]")));
		// Click on update button
		driver.findElement(By.xpath("//tr[1]//td[1]//div[1]//div[5]//button[1]")).click();
		// Wait
		Thread.sleep(1000);
		// Wait until update modal display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-assigned-quizzes-modal")));
		// Click on assign other quizzes button
		driver.findElement(By.id("assign-other-quizzes-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Click select button in the second line in the table to assign this quiz
		driver.findElement(By.cssSelector(
				"div.main:nth-child(2) div.content div.table-responsive-lg div.modal.fade.bd-example-modal-lg.show div.modal-dialog.modal-lg div.modal-content div.modal-body div.collapse.show:nth-child(4) div.card.card-body div.row div.col-lg-12 div.row:nth-child(2) div.col-lg-12.list-group a.list-group-item.list-group-item-action.d-flex.justify-content-between.align-items-center:nth-child(3) > button.btn.btn-circle.btn-sm"))
				.click();
		// Wait
		Thread.sleep(1000);
		// Click select button in the third line in the table to assign this quiz
		driver.findElement(By.cssSelector(
				"div.main:nth-child(2) div.content div.table-responsive-lg div.modal.fade.bd-example-modal-lg.show div.modal-dialog.modal-lg div.modal-content div.modal-body div.collapse.show:nth-child(4) div.card.card-body div.row div.col-lg-12 div.row:nth-child(2) div.col-lg-12.list-group a.list-group-item.list-group-item-action.d-flex.justify-content-between.align-items-center:nth-child(4) > button.btn.btn-circle.btn-sm"))
				.click();
		// Wait
		Thread.sleep(1000);
		// Click remove button that appear in front of the last quiz added to remove it
		driver.findElement(By.cssSelector(
				"div.main:nth-child(2) div.content div.table-responsive-lg div.modal.fade.bd-example-modal-lg.show div.modal-dialog.modal-lg div.modal-content div.modal-body div.row:nth-child(1) div.col-lg-6:nth-child(2) div.card div.card-body ul.list-group.list-group-flush:nth-child(2) li:nth-child(1) div.notice.notice-info > button.icon-button.float-right:nth-child(2)"))
				.click();
		// Wait
		Thread.sleep(1000);
		// Select a date for the assigned quizzes added
		driver.findElement(By.id("date-update")).sendKeys("21/03/2019");
		// Wait
		Thread.sleep(1000);
		// Click save button
		driver.findElement(By.id("save-update-assigned-quizzes-to-candidates-btn")).click();
		// Wait
		Thread.sleep(1000);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
