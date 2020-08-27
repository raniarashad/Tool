package pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class test extends PageBase {

	public test(WebDriver driver) {
		super(driver);
		js = (JavascriptExecutor) driver;
		Driver = driver;
	}

	JavascriptExecutor js;	
	WebDriver Driver;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	@FindBy(xpath = "//button[@class='cookie-notice__agree-button button']")
	WebElement acceptCookies;

	
	public void testOnly() throws IOException {
		
		ClickButton(acceptCookies);
		sleep(10);
		System.out.println("begin");
		
		System.out.println("Please enter the Class Name of the image to download this : ");
		String ImageSelector = reader.readLine();
		List <WebElement> ThumbnailImage_Listing  = (List<WebElement>) Driver.findElements(By.className(ImageSelector));

		for(int j = 0; j < ThumbnailImage_Listing.size(); j++)
		{
			WebElement Image = Driver.findElement(By.className(ImageSelector));
			js.executeScript("window.scrollBy(0,1000)");
			sleep(10);
			Actions action= new Actions(driver);
			action.contextClick(Image).build().perform();
			action.sendKeys(Keys.CONTROL, "s").build().perform();

		} 
	}
}
