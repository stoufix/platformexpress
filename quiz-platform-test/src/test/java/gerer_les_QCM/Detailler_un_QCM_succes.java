package gerer_les_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Detailler_un_QCM_succes {

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
	public void detailQuiz() throws InterruptedException {

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

		/********* Detail quiz *********/
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Get quiz title for PDF name
		String quizTitle = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[1]")).click();

		// Wait for details modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("detailsmodal")));
		Thread.sleep(3000);

		// Click on exit button
		driver.findElement(By.id("exit-detail-quiz")).click();
		Thread.sleep(1000);

		// Click on MyQCM menu button
		driver.findElement(By.id("my-quiz-menu")).click();
		Thread.sleep(2000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[6]//button[1]")).click();

		// Wait for details modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("detailsmodal")));
		Thread.sleep(4000);

		// Click on exit button
		driver.findElement(By.id("download-detail-quiz")).click();
		Thread.sleep(3000);

		// Open PDF file
		driver.get("file:///C:/Users/moez.barkia/Downloads/" + quizTitle + ".pdf");
		Thread.sleep(9000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
