package pages;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Testpage extends PageBase {

	public Testpage(WebDriver driver) {
		super(driver);
		js = (JavascriptExecutor) driver;
		Driver = driver;
	}

	JavascriptExecutor js;	
	WebDriver Driver;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	@FindBy(xpath = "//button[@class='cookie-notice__agree-button button']")
	WebElement acceptCookies;
	
	public void Test() throws IOException
	{
		ClickButton(acceptCookies);
		sleep(10);
		System.out.println("begin");
		js.executeScript("window.scroll({top: 1000, left: 0, behavior: 'smooth'});");
		sleep(10);
		List <WebElement> ImageList = Driver.findElements(By.cssSelector(".product-page__images-container img"));
		int count = 0 ;
		for (int i = 0 ; i<ImageList.size(); i++)
		{		
			WebElement ImageList2 = ImageList.get(i);
			String ImageSrc = ImageList2.getAttribute("src");
			URL ImageURL = new URL (ImageSrc);
			BufferedImage SaveImage = ImageIO.read(ImageURL);
			ImageIO.write(SaveImage, "png", new File(count +".png"));	
			count++;
		}
		System.out.println();
	}

}
