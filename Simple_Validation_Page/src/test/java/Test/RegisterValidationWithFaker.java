package Test;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.javafaker.Faker;

public class RegisterValidationWithFaker {

	public static void main(String[] args) {

		Faker faker = new Faker();
		
		int minimumLength = 8;
		int maximumLength = 16;
		boolean includeUppercase = true;
		
		String email = faker.internet().emailAddress();
		String password = faker.internet().password(minimumLength, maximumLength, includeUppercase);
		int day = faker.number().numberBetween(1, 30);
		int year = faker.number().numberBetween(1900, 2000);
		
		String dayAsString = String.valueOf(day);
		String yearAsString = String.valueOf(year);

				
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;				
		driver.get("https://allegro.pl/");
		driver.manage().window().maximize();
		System.out.println(driver.getTitle());
		driver.findElement(By.cssSelector("button._13q9y._8tsq7._7qjq4._qozgg")).click();
		driver.findElement(By.cssSelector("span.fee54_3VuhJ.opbox-metrum-header__account-name-wrapper")).click();
		driver.findElement(By.cssSelector("a._13q9y._8hkto._7qjq4")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("day")).sendKeys(dayAsString);

		Select s = new Select(driver.findElement(By.xpath("//select[@name='month']")));
		s.selectByValue("1");
				
		driver.findElement(By.id("year")).sendKeys(yearAsString);
		driver.findElement(By.cssSelector("label.m-label.ng-scope")).click();
		driver.findElement(By.xpath("//label[@for='allegroMarketing']")).click();
		driver.findElement(By.xpath("//label[@for='thirdPartyMarketing']")).click();
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS) ;				
		driver.findElement(By.xpath("//button[contains(@type,'submit')]")).click();
				
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Zadanie reCAPTCHA']")));
		
		if(driver.findElement(By.id("rc-imageselect")).isDisplayed()) {
			System.out.println("reCAPTCHA stopped validation");
		}
		else {
			System.out.println("Registration completed");
		}

	}

}
