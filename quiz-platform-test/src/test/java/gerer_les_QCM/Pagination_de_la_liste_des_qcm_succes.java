package gerer_les_QCM;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Pagination_de_la_liste_des_qcm_succes {

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
	public void paginateQuestion() throws InterruptedException {

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

		/********* Navigate to the list of question *********/
		// Wait for first quiz to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));
		// Wait
		Thread.sleep(1000);

		// Click on edit button
		driver.findElement(By.xpath("//tr[1]//td[7]//button[2]")).click();

		// Wait for search input of question list to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-question")));
		Thread.sleep(1000);

		/********* Pagination actions *********/
		// Click on next if enable
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Suivante')]")).isEnabled();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Suivante')]")).click();
		}
		Thread.sleep(1000);
		// Click on next if enable
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Suivante')]")).isEnabled();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Suivante')]")).click();
		}
		Thread.sleep(1000);

		// Click on previous if enable
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Précédente')]")).isEnabled();
		} catch (NoSuchElementException e) {
			driver.findElement(By.xpath("//a[contains(text(),'Précédente')]")).click();
		}
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
