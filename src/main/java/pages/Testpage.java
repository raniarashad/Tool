package pages;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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
		sleep(4);
		WebElement AddtoBagBtn = Driver.findElement(By.className("add-to-bag__add"));
		String SKU = AddtoBagBtn.getAttribute("data-partnumber");

		List <WebElement> ImageList = Driver.findElements(By.cssSelector(".product-page__images-container img"));
		ArrayList<String> imagesrclist =new ArrayList<String>();
		int count = 0 ;
		for (int i = 0 ; i<ImageList.size(); i++)
		{		
			WebElement ImageList2 = ImageList.get(i);
			String ImageSrc = ImageList2.getAttribute("src");
			imagesrclist.add(ImageSrc);

		}
		String FOLDER = "C:\\Users\\rania.rashad\\Desktop\\Automation\\AutomationFramework-master\\";
		for (int j = 0 ; j<imagesrclist.size() ; j++)
		{
			URL  ImageURL = new URL (imagesrclist.get(j));
			BufferedImage SaveImage = ImageIO.read(ImageURL);
			File file = new File(FOLDER + j);
			file.mkdirs();
			ImageIO.write(SaveImage, "png", new File(file , SKU +".png"));	
		}
	}
}
