package pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class Scrapping_InnerProductDataPage extends PageBase {

	public Scrapping_InnerProductDataPage(WebDriver driver) {
		super(driver);
		js = (JavascriptExecutor) driver;
		Driver = driver;

	}

	JavascriptExecutor js;	
	WebDriver Driver;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	@FindBy(xpath = "//button[@class='cookie-notice__agree-button button']")
	WebElement acceptCookies;

	public void SavingProductImages () throws IOException
	{
		ClickButton(acceptCookies);
		sleep(10);
		System.out.println("begin");

		System.out.println("Please enter the Class Name : ");
		String selector = reader.readLine();
		List <WebElement> ImageTag  = (List<WebElement>) Driver.findElements(By.className(selector));
		
	//	System.out.println("Please enter the Class Name to download inner product image : ");
	//	String ImageSelector = reader.readLine();
	//	WebElement InnerProductImage  = Driver.findElement(By.className(ImageSelector));

		for (int i = 0; i <ImageTag.size(); i++) 
		{ 
			WebElement listingItems = ImageTag.get(i);
			try {
				listingItems.click();		
				
				// String URL = Driver.getCurrentUrl();
				//System.out.println(URL);
			}
			catch (StaleElementReferenceException e) {
				ImageTag  = (List<WebElement>) Driver.findElements(By.className(selector));
				listingItems = ImageTag.get(i);
				listingItems.click();
				String URL2 = Driver.getCurrentUrl();
				System.out.println(URL2);
			}  
			Driver.navigate().back();
			sleep(30);
			//js.executeScript("window.scrollBy(0,700)");
		}		

		/*Iterator<WebElement> iter = ImageTag.iterator();

		while(iter.hasNext()) {
		    WebElement we = iter.next();
		    we.click();
		    String URL = Driver.getCurrentUrl();
		    System.out.println(URL);
		    Driver.navigate().back();

		    // do something in else perhaps
		    }
		 */
	}

}


