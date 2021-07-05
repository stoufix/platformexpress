package gerer_les_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajouter_un_QCM_succes {

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
	public void addQuiz() throws InterruptedException {

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

		// Wait for quiz add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-quiz-btn")));

		/********* Add Quiz *********/
		// Click on add button
		driver.findElement(By.id("add-quiz-btn")).click();
		// Wait
		Thread.sleep(1000);

		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write title
		driver.findElement(By.id("title-add")).sendKeys("QCM Java Débutant");
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.id("description-add"))
				.sendKeys("QCM Java niveau débutant pour tester les notions de base de programmation Java");
		// Wait
		Thread.sleep(1000);

		// Write description
		driver.findElement(By.id("duration-add")).sendKeys("30");
		// Wait
		Thread.sleep(1000);

		// Select degree
		Select degree = new Select(driver.findElement(By.id("degree-add")));
		degree.selectByVisibleText("Débutant");
		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("visibility-add")).click();
		// Wait
		Thread.sleep(1000);

		// click on add button
		driver.findElement(By.id("submit-add-quiz")).click();

		// Wait
		Thread.sleep(2000);

		/********* Verify the adding *********/
		// Wait for assign question interface to display with quiz title on the top of
		// the page
		webDriverWait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'QCM Java Débutant')]")));

	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
