package gerer_les_QCM;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Supp_un_QCM_echec {

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
	public void deleteQuizFail() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		/********* search for quiz to delete *********/

		// Wait for activity menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("assign-quizzes")));
		// Wait
		Thread.sleep(1000);

		// Navigate to activities section
		driver.findElement(By.id("assign-quizzes")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for activity search input to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Chaima Zamni')]")));
		// Wait
		Thread.sleep(1000);

		// Open collapse to display the list of quizzes of the first candidate
		driver.findElement(By.id("show-quizzes-list-btn")).click();
		// Wait
		Thread.sleep(1000);

		// Get the quiz title to delete
		String quizToDelete = driver
				.findElement(
						By.xpath("//div[@class='collapse show']//div[@class='col-3'][contains(text(),'Angular 4')]"))
				.getText();
		// Remove first word to get the real title of the quiz
		quizToDelete = quizToDelete.substring(5);
		// Wait
		Thread.sleep(1000);

		/********* delete quiz fail *********/
		// Wait for quiz menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quizzes-management")));

		// Navigate to quiz section
		driver.findElement(By.id("quizzes-management")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Write search term
		driver.findElement(By.id("search-quiz")).sendKeys(quizToDelete);
		// Wait
		Thread.sleep(1000);
		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[3]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for delete modal to display
		webDriverWait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Oui')]")));
		// click on confirm delete button
		driver.findElement(By.xpath("//button[contains(text(),'Oui')]")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for error modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'ok')]")));
		// click on delete button
		driver.findElement(By.xpath("//button[contains(text(),'ok')]")).click();
		// Wait
		Thread.sleep(1000);

		/********* Verify the deleting of quiz *********/
		// Wait for quiz search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-quiz")));

		// Write search term
		driver.findElement(By.id("search-quiz")).clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("search-quiz")).sendKeys(quizToDelete);
		// Wait
		Thread.sleep(1000);

		// Wait result to display to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first quiz of the search result
		String quizTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(quizTitle, quizToDelete);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
