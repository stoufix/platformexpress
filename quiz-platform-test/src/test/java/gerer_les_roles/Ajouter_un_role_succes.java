package gerer_les_roles;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Ajouter_un_role_succes {
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
	public void addRole() throws InterruptedException {

		/********* Login *********/
		// Write userName
		driver.findElement(By.id("username")).sendKeys("maha");
		// Write password
		driver.findElement(By.id("password")).sendKeys("maha");
		// Click on login button
		driver.findElement(By.id("submit")).click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 20);

		// Wait for activity menu to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("roles-management")));

		// Navigate to activities section
		driver.findElement(By.id("roles-management")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for activity add button to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-role-btn")));

		/********* Add activity *********/
		// Click on add button
		driver.findElement(By.id("add-role-btn")).click();
		// Wait
		Thread.sleep(1000);
		// Wait for add modal to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addModal")));

		// Write name
		driver.findElement(By.id("name-add")).sendKeys("Ingénieur");
		// Wait
		Thread.sleep(1000);
		// Write description
		driver.findElement(By.id("description-add")).sendKeys("Rôle d'ingénieur");
		// Wait
		Thread.sleep(1000);
		// click on access activities check box
		driver.findElement(By.id("activities")).click();
		// Wait
		Thread.sleep(1000);

		// click on authority check box
		driver.findElement(
				By.xpath("//p-listbox[@name='selectedPrivilegesQuizzes']//span[contains(text(),'SHOW_QUIZ')]")).click();
		// Wait
		Thread.sleep(1000);
		driver.findElement(
				By.xpath("//p-listbox[@name='selectedPrivilegesQuestions']//span[contains(text(),'SHOW_QUESTION')]"))
				.click();
		// Wait
		Thread.sleep(1000);
		driver.findElement(
				By.xpath("//p-listbox[@name='selectedPrivilegesQuestions']//span[contains(text(),'CREATE_QUESTION')]"))
				.click();
		// Wait
		Thread.sleep(1000);
		driver.findElement(
				By.xpath("//p-listbox[@name='selectedPrivilegesDegrees']//span[contains(text(),'SHOW_DEGREE')]"))
				.click();
		// Wait
		Thread.sleep(1000);
		// click on add button
		driver.findElement(By.id("submit-add-role")).click();
		// Wait
		Thread.sleep(2000);

		/********* Verify the adding *********/
		// Wait for role search input to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-role")));

		// Write search term with the name of the new role
		driver.findElement(By.id("search-role")).sendKeys("Rôle d'ingénieur");
		// Wait
		Thread.sleep(1000);

		// Wait result to display
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[1]//td[1]")));

		// Get the name of first role of the search result
		String nameActivity = driver.findElement(By.xpath("//tr[1]//td[1]")).getText();

		// Compare it with the expected name
		assertEquals(nameActivity, "Ingénieur");
		// Wait
		Thread.sleep(1000);
		// Get the description of first activity of the search result
		String descActivity = driver.findElement(By.xpath("//tr[1]//td[2]")).getText();

		// Compare it with the expected name
		assertEquals(descActivity, "Rôle d'ingénieur");
		// Wait
		Thread.sleep(1000);
	}

	@AfterClass
	public void afterClass() throws InterruptedException {
		Thread.sleep(200);
		driver.close();
	}
}
