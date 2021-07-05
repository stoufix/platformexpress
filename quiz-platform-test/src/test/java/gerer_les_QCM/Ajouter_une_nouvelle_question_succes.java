package gerer_les_QCM;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajouter_une_nouvelle_question_succes {
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
	public void addQuestion() throws InterruptedException {

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

		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();

		// Wait for add question button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-question-quiz-btn")));

		/********* Add question *********/
		// Click on add button
		driver.findElement(By.id("add-question-quiz-btn")).click();

		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addQuestionModal")));

		// Write description
		driver.findElement(
				By.xpath("//form[@class='ng-untouched ng-pristine ng-invalid']//div[@class='ql-editor ql-blank']"))
				.sendKeys("Les navigateurs sont-ils capables d'interpréter le TypeScript directement?");
		// Wait
		Thread.sleep(1000);

		// Write mark
		driver.findElement(By.id("mark-add-question")).sendKeys("2");
		
		// Wait
		Thread.sleep(1000);

		// Select technology
		Select technology = new Select(driver.findElement(By.id("technology-add-question")));
		technology.selectByVisibleText("Angular");

		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("visibility")).click();
		
		// Wait
		Thread.sleep(1000);

		// Write proposition
		driver.findElement(By.id("proposition-add")).sendKeys("Oui");

		// Wait
		Thread.sleep(1000);

		// Click to add proposition
		driver.findElement(By.id("add-proposition-btn")).click();

		// Wait
		Thread.sleep(1000);

		// Wait for proposition update button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[1]//button[1]")));

		// Click to update proposition
		driver.findElement(By.xpath("//td[1]//button[1]")).click();

		// Wait
		Thread.sleep(1000);

		// Wait for confirm proposition update button to display
		webDriverWait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='col-9']//button[@type='button']")));

		// Wait
		Thread.sleep(1000);

		// click on confirm proposition update button
		driver.findElement(By.xpath("//div[@class='col-9']//button[@type='button']")).click();

		// Wait
		Thread.sleep(1000);

		// Wait for proposition delete button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[2]//button[1]")));

		// Click to delete proposition
		driver.findElement(By.xpath("//td[2]//button[1]")).click();

		// Wait
		Thread.sleep(1000);

		// Write first proposition
		driver.findElement(By.id("proposition-add")).sendKeys("Non");

		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("valid")).click();

		// Wait
		Thread.sleep(1000);

		// Click to add first proposition
		driver.findElement(By.id("add-proposition-btn")).click();

		// Wait
		Thread.sleep(1000);

		// Write second proposition
		driver.findElement(By.id("proposition-add")).sendKeys("Oui");

		// Wait
		Thread.sleep(1000);

		// Click on check-box
		driver.findElement(By.id("valid")).click();

		// Click to add second proposition
		driver.findElement(By.id("add-proposition-btn")).click();

		// click on add button
		driver.findElement(By.id("submit-add-question-quiz")).click();

		// Wait
		Thread.sleep(1000);

		/********* Verify the adding *********/
		// Wait list of questions to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the description of first question of the list
		String descQuestion = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected description
		assertEquals(descQuestion, "Les navigateurs sont-ils capables d'interpréter le TypeScript directement?");

		// Get the creator of first question of the list
		String creatorQuestion = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected creator
		assertEquals(creatorQuestion, "Maha Ben Said");
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}

}
