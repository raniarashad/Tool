package pages;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;


public class ScrapData_InnerProductPage extends PageBase {

	public ScrapData_InnerProductPage(WebDriver driver) {
		super(driver);
		js = (JavascriptExecutor) driver;
		Driver = driver;
	}

	JavascriptExecutor js;	
	WebDriver Driver;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	@FindBy(xpath = "//button[@class='cookie-notice__agree-button button']")
	WebElement acceptCookies;

	List<String> all_elements_href =new ArrayList<>();

	public void ScrappingInnerProductURL() throws IOException {

		ClickButton(acceptCookies);
		sleep(10);
		System.out.println("begin");

		System.out.println("Please enter the Class Name : ");
		String selector = reader.readLine();
		List <WebElement> theListOfItems  = (List<WebElement>) Driver.findElements(By.className(selector));

		System.out.println("Please Enter the File Path that data will be saved in : ");
		String FilePath = reader.readLine();
		File source  = new File(FilePath);
		sleep(10);

		for (int i = 0; i <theListOfItems.size(); i++) 
		{
			String list = theListOfItems.get(i).getAttribute("href");
			all_elements_href.add(list);
			js.executeScript("window.scrollBy(0,2000)");
			sleep(10);

			FileInputStream input = new FileInputStream(source);
			XSSFWorkbook wb = new XSSFWorkbook(input);
			XSSFSheet sheet = wb.getSheetAt(0);
			sheet.createRow(i).createCell(0).setCellValue(list);
			FileOutputStream output = new FileOutputStream(source);
			wb.write(output);
			wb.close();				
		}
	}

	public void Open_Href_FromList () throws IOException
	{
		ClickButton(acceptCookies);
		sleep(10);
		System.out.println("begin");

		System.out.println("Please enter the Class Name to get the Product Name : ");
		String Pro_Name = reader.readLine();
		
		System.out.println("Please enter the Class Name to get the Product Price : ");
		String Pro_Price = reader.readLine();
		
		System.out.println("Please enter the Class Name to get the Product Brans");
		String Pro_Brand = reader.readLine();
		
		System.out.println("Please Enter the File Path that data will be saved in : ");
		String FilePath = reader.readLine();
		File source  = new File(FilePath);

		for (String href : all_elements_href)
		{
			Driver.navigate().to(href);
			sleep(5);
			WebElement ProductName  = Driver.findElement(By.className(Pro_Name));
			String InnerProduct_ProName = ProductName.getText();
			sleep(5);
			
			WebElement ProductPrice = Driver.findElement(By.className(Pro_Price));
			String Price = ProductPrice.getText();
			
			WebElement ProductBrand = Driver.findElement(By.className(Pro_Brand));
			String Brand = ProductBrand.getText();
			sleep(5);
			
			//WriteDataToExcelSheet(InnerProduct_ProName ,Price , Brand ,source );

		}
		System.out.println("Done");
	}
	
	
	
	
	public void ScrappingInnerProductData() throws IOException 
	{
		ClickButton(acceptCookies);
		sleep(10);
		System.out.println("begin");

		System.out.println("Please enter the Class Name to navigate to inner product page : ");
		String ImageSelectorList = reader.readLine();
		List <WebElement> ListofItems  = (List<WebElement>) Driver.findElements(By.className(ImageSelectorList));

		System.out.println("Please enter the Class Name to get the Product Name : ");
		String Pro_Name = reader.readLine();

		System.out.println("Please enter the Class Name to get the Product Price : ");
		String Pro_Price = reader.readLine();

		//System.out.println("Please enter the Class Name to get the Brand  : ");
		//String Pro_Brand = reader.readLine();
		
		System.out.println("Please Enter the File Path that data will be saved in : ");
		String FilePath = reader.readLine();
		File source  = new File(FilePath);
		
		sleep(10);
		
		for (int i = 0 ; i<ListofItems.size(); i++)
		{
			WebElement ProductCard = ListofItems.get(i);
			try {
				ProductCard.click();			
			}
			catch (StaleElementReferenceException e) {
				ListofItems  = (List<WebElement>) Driver.findElements(By.className(ImageSelectorList));
				ProductCard = ListofItems.get(i);
				ProductCard.click();
				sleep(5);
				WebElement ProductName  = Driver.findElement(By.className(Pro_Name));
				String Name = ProductName.getText();
				sleep(5);
				WebElement ProductPrice = Driver.findElement(By.className(Pro_Price));
				String Price = ProductPrice.getText();
			
				sleep(5);
				//WriteDataToExcelSheet(Name ,Price ,source );
	
			}  
			sleep(5);
			Driver.navigate().back();
			//js.executeScript("window.scrollBy(0,700)");
		}

	}
	
	public void WriteDataToExcelSheet (int index , String Name, String Price , String Brand ,File Source) throws IOException
	{
		FileInputStream input = new FileInputStream(Source);
		XSSFWorkbook wb = new XSSFWorkbook(input);
		XSSFSheet sheet = wb.getSheetAt(0);

		//sheet.createRow(0).createCell(0).setCellValue("Product Name");
		//sheet.getRow(0).createCell(1).setCellValue("Product Price");
		sleep(5);
		sheet.createRow(index).createCell(0).setCellValue(Name);
		sheet.getRow(index).createCell(1).setCellValue(Price);
		//sheet.getRow(i).createCell(2).setCellValue(Brand);
		FileOutputStream output = new FileOutputStream(Source);
		wb.write(output);
		wb.close();	
	}

}


