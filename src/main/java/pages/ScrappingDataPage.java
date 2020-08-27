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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScrappingDataPage extends PageBase {
	public ScrappingDataPage(WebDriver driver) {
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

		sleep(10);

		for (int i = 0; i <theListOfItems.size(); i++) 
		{
			String list = theListOfItems.get(i).getAttribute("href");
			all_elements_href.add(list);
			js.executeScript("window.scrollBy(0,2000)");
			sleep(10);		
		}
	}

	public void Open_Href_FromList () throws IOException
	{
		System.out.println("let's begin to sniff the product data");

		System.out.println("Please enter the Class Name to get the Product Name : ");
		String Pro_Name = reader.readLine();
		
		System.out.println("Please enter the Class Name to get the Product Price : ");
		String Pro_Price = reader.readLine();
		
		System.out.println("Please enter the Class Name to get the Product Brand");
		String Pro_Brand = reader.readLine();
		
		System.out.println("Please enter the Class Name to get the Color name");
		String Pro_Color = reader.readLine();
		
	//	System.out.println("Please enter the selector to get the Product Details");
	//	String Pro_Details = reader.readLine();
		
		System.out.println("Please enter the Class name of Product Image");
		String Pro_Image = reader.readLine();
		
		System.out.println("Please Enter the File Path that data will be saved in : ");
		String FilePath = reader.readLine();
		
		List<Product> productelements =new ArrayList<Product>();
		
		for (String href : all_elements_href)
		{
			Product prod = new Product();
			Driver.navigate().to(href);
			sleep(5);
			WebElement ProductName  = Driver.findElement(By.className(Pro_Name));
			prod.Name = ProductName.getText();
			
			WebElement ProductPrice = Driver.findElement(By.className(Pro_Price));
			prod.Price = ProductPrice.getText();
			
			WebElement ProductBrand = Driver.findElement(By.className(Pro_Brand));
			prod.Brand = ProductBrand.getText();

			WebElement ProductColor = Driver.findElement(By.className(Pro_Color));
			prod.Color = ProductColor.getText();
			
			WebElement ProductDetails = Driver.findElement(By.cssSelector(".description__sidebar-content p span"));
			prod.Details = ProductDetails.getText();
			
			
			productelements.add(prod);
			
		}
		WriteDataToExcelSheet(FilePath, productelements);
		
		System.out.println("Done");
	}
	
	public void WriteDataToExcelSheet (String Filepath , List<Product> products) throws IOException
	{
		File source  = new File(Filepath);
		FileInputStream input = new FileInputStream(source);
		XSSFWorkbook wb = new XSSFWorkbook(input);
		XSSFSheet sheet = wb.getSheetAt(0);
	//	sheet.createRow(0).createCell(0).setCellValue("Product Name");
	//  sheet.getRow(0).createCell(1).setCellValue("Product Price");
		for (int i = 0 ; i < products.size(); i++)
		{		

		sleep(5);
		sheet.createRow(i).createCell(0).setCellValue(products.get(i).Name);
		sheet.getRow(i).createCell(1).setCellValue(products.get(i).Price);
	    sheet.getRow(i).createCell(2).setCellValue(products.get(i).Brand);
	    sheet.getRow(i).createCell(3).setCellValue(products.get(i).Color);
	    sheet.getRow(i).createCell(4).setCellValue(products.get(i).Details);

		}
		FileOutputStream output = new FileOutputStream(source);
		wb.write(output);
		wb.close();	
		
	}

	
}

