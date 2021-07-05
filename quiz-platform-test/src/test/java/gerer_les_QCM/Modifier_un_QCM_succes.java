package gerer_les_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Modifier_un_QCM_succes {

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
	public void updateQuiz() throws InterruptedException {

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

		/********* Edit quiz *********/
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();

		// Wait for search input of question list to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-question")));

		// Search for question with java
		driver.findElement(By.id("search-question")).sendKeys("java");
		// Wait
		Thread.sleep(1000);
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]//p[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on add question button
		driver.findElement(By.xpath("//tr[1]//td[3]//span[3]//button[1]")).click();
		// Wait for add mark modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addMarkModal")));
		// Write mark
		driver.findElement(By.id("mark-add")).sendKeys("3");
		// Wait
		Thread.sleep(1000);
		// Click on add mark button
		driver.findElement(By.id("submit-add-mark")).click();
		driver.findElement(By.id("exit-add-mark")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]//p[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on add question button
		driver.findElement(By.xpath("//tr[4]//td[3]//span[3]//button[1]")).click();
		// Wait for add mark modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addMarkModal")));
		// Write mark
		driver.findElement(By.id("mark-add")).sendKeys("3");
		// Wait
		Thread.sleep(1000);
		// Click on add mark button
		driver.findElement(By.id("submit-add-mark")).click();
		driver.findElement(By.id("exit-add-mark")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("update-quiz-btn")));
		// Wait
		Thread.sleep(1000);
		/********* Update note *********/
		// Click on update note button
		driver.findElement(By.id("open-note-update")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for form and submit update note button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-update-note")));

		// Clear note field
		WebElement titleField = driver.findElement(By.id("note-update"));
		titleField.sendKeys((Keys.chord(Keys.CONTROL, "a")));
		titleField.sendKeys(Keys.BACK_SPACE);
		titleField.clear();
		// Wait
		Thread.sleep(1000);
		driver.findElement(By.id("note-update")).sendKeys("1");
		// Wait
		Thread.sleep(1000);

		// Click on update note button
		driver.findElement(By.id("submit-update-note")).click();
		// Wait
		Thread.sleep(1000);

		// Click on update quiz button
		driver.findElement(By.id("update-quiz-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for update button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
