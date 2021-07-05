package gerer_les_QCM;

import static org.testng.Assert.assertNotEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supprimer_un_QCM_succes {

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
	public void deleteQuiz() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for quiz menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quizzes-management")));

		// Navigate to quiz section
		driver.findElement(By.id("quizzes-management")).click();
		// Wait
		Thread.sleep(1000);

		/********* Delete quiz *********/
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);
		// Get the title of the quiz to delete for verify process
		String deletedQuizTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
		// Get the description of the quiz to delete for verify process
		String deletedQuizDesc = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[3]")).click();

		// Wait for delete modal to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));

		// Click on delete button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();

		/********* Verify the deleting of quiz *********/
		// Wait list of questions to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);
		// Get the title of first question of the list
		String quizTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();
		// Compare it with title of deleted quiz
		assertNotEquals(quizTitle, deletedQuizTitle);
		// Get the description of first question of the list
		String quizDesc = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();
		// Compare it with description of deleted quiz
		assertNotEquals(quizDesc, deletedQuizDesc);
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
