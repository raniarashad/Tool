package pages;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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

		//System.out.println("Please enter the Class Name : ");
		//String selector = reader.readLine();
		List <WebElement> theListOfItems  = (List<WebElement>) Driver.findElements(By.className("product-tile__imagery"));

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
		System.out.println("let's begin to sniff the products data");

		//System.out.println("Please enter the Class Name to get the Product Name : ");
		//String Pro_Name = reader.readLine();

		//System.out.println("Please enter the Class Name to get the Product Price : ");
		//String Pro_Price = reader.readLine();

		//System.out.println("Please enter the Class Name to get the Product Brand");
		//String Pro_Brand = reader.readLine();

		//System.out.println("Please enter the Class Name to get the Color name");
		//String Pro_Color = reader.readLine();

		//	System.out.println("Please enter the selector to get the Product Details");
		//	String Pro_Details = reader.readLine();

		//	System.out.println("Please enter the Class name of Product Image");
		//	String Pro_Image = reader.readLine();

		System.out.println("Please Enter the File Path that data will be saved in : ");
		String FilePath = reader.readLine();

		List<Product> productelements =new ArrayList<Product>();


		for (String href : all_elements_href)
		{
			Driver.navigate().to(href);
			sleep(5);
			//	List<WebElement> ColorList = Driver.findElements(By.cssSelector("[data-attribute='PRODUCT_ATTR_PRODUCT_ATTR_COLOUR'] button"));
			//for ( int j = 0 ; j<ColorList.size(); j++ )
			//{
			//	WebElement imagebutton = ColorList.get(j);
			//	imagebutton.click();

			WebElement ProductName  = Driver.findElement(By.className("product__name"));

			WebElement ProductPrice = Driver.findElement(By.className("price-display__from"));

			WebElement ProductBrand = Driver.findElement(By.className("product__brand"));

			WebElement ProductColor = Driver.findElement(By.className("attribute-selector__selected"));

			WebElement ProductDetails = Driver.findElement(By.cssSelector(".description__sidebar-content p span"));

			WebElement AddtoBagBtn = Driver.findElement(By.className("add-to-bag__add"));
			
			WebElement ProductCode = Driver.findElement(By.className("description__style"));

			String SKU = AddtoBagBtn.getAttribute("data-partnumber");
			sleep(10);
			List <WebElement> SizeList = Driver.findElements(By.cssSelector("[data-attribute='PRODUCT_ATTR_SIZE_UK'] button"));
			for (int k=0 ;k<SizeList.size();k++ )
			{
				Product prod = new Product();
				WebElement Sizelisting = SizeList.get(k);
				prod.Size= Sizelisting.getText();
				prod.Name = ProductName.getText();
				prod.Price = ProductPrice.getText();
				prod.Price = prod.Price.replace("FROM £", "").replace("£", "");
				prod.Brand = ProductBrand.getText();
				prod.Color = ProductColor.getText();
				prod.Details = ProductDetails.getText();	
				prod.ProductCode = ProductCode.getText();
				prod.ProductCode = prod.ProductCode.replace("Style #: ", "");
				prod.SKUcode = SKU;
				prod.Stock = "100";
				prod.ColorCode = "#000000";
				prod.shipmentCost = "0";
				prod.published = "yes";
				prod.Active = "yes";
				prod.CategoryRow = "2";
				prod.BrandEnable = "yes";
				productelements.add(prod);
				//	URL ImageURL = new URL (prod.ImageSrc);
				//	BufferedImage SaveImage = ImageIO.read(ImageURL);
				//	ImageIO.write(SaveImage, "png", new File(countf +".png"));	
				//	countf++;
				//}
			}
			js.executeScript("window.scroll({top: 1000, left: 0, behavior: 'smooth'});");
			List <WebElement> ImageList = Driver.findElements(By.cssSelector(".product-page__images-container .product-image .product-image__image"));
			int count = 0 ;
			for (int i = 0 ; i<ImageList.size(); i++)
			{		
				WebElement ImageList2 = ImageList.get(i);
				String ImageSrc = ImageList2.getAttribute("src");
				URL ImageURL = new URL (ImageSrc);
				BufferedImage SaveImage = ImageIO.read(ImageURL);
				sleep(5);
				ImageIO.write(SaveImage, "png", new File(SKU + count +".png"));	
				count++;
			}
		}
		WriteDataToExcelSheet(FilePath, productelements);
		System.out.println("Done");
	}

	public void WriteDataToExcelSheet (String Filepath , List<Product> products) throws IOException
	{
		File source = new File(Filepath);
		FileInputStream input = new FileInputStream(source);
		XSSFWorkbook wb = new XSSFWorkbook(input);
		XSSFSheet CatSheet = wb.getSheetAt(0);
		CatSheet.createRow(0).createCell(0).setCellValue("Cat1En");
		CatSheet.createRow(0).createCell(1).setCellValue("Cat1Ar");
		CatSheet.createRow(0).createCell(2).setCellValue("Cat1Enable");
		XSSFSheet sheet = wb.createSheet("Products");
		sheet.createRow(1).createCell(0).setCellValue("ProductCode");
		sheet.getRow(1).createCell(1).setCellValue("SKU");
		sheet.getRow(1).createCell(2).setCellValue("NameEn");
	    sheet.getRow(1).createCell(3).setCellValue("NameAr");
		sheet.getRow(1).createCell(4).setCellValue("BriefDescription En");
		sheet.getRow(1).createCell(5).setCellValue("BriefDescription Ar");
		sheet.getRow(1).createCell(6).setCellValue("Brand");
		sheet.getRow(1).createCell(7).setCellValue("Brand Ar");
		sheet.getRow(1).createCell(8).setCellValue("BrandEnable");
		sheet.getRow(1).createCell(9).setCellValue("Price");
		sheet.getRow(1).createCell(10).setCellValue("Size");
		sheet.getRow(1).createCell(11).setCellValue("ColorEn");
		sheet.getRow(1).createCell(12).setCellValue("ColorAr");
		sheet.getRow(1).createCell(13).setCellValue("ColorCode");
		sheet.getRow(1).createCell(14).setCellValue("Stock");
		sheet.getRow(1).createCell(15).setCellValue("Published");
		sheet.getRow(1).createCell(16).setCellValue("Active");
		sheet.getRow(1).createCell(17).setCellValue("ShipmentCost");
		sheet.getRow(1).createCell(18).setCellValue("CategoryRow");
		
		for (int i = 0 ; i < products.size(); i++)
		{		
			sleep(5);
			sheet.createRow(i+2).createCell(0).setCellValue(products.get(i).ProductCode);
			sheet.getRow(i+2).createCell(1).setCellValue(products.get(i).SKUcode);
			sheet.getRow(i+2).createCell(2).setCellValue(products.get(i).Name);
			sheet.getRow(i+2).createCell(3).setCellValue(products.get(i).Name);
			sheet.getRow(i+2).createCell(4).setCellValue(products.get(i).Details);
			sheet.getRow(i+2).createCell(5).setCellValue(products.get(i).Details);
			sheet.getRow(i+2).createCell(6).setCellValue(products.get(i).Brand);
			sheet.getRow(i+2).createCell(7).setCellValue(products.get(i).Brand);
			sheet.getRow(i+2).createCell(8).setCellValue(products.get(i).BrandEnable);
			sheet.getRow(i+2).createCell(9).setCellValue(products.get(i).Price);
			sheet.getRow(i+2).createCell(10).setCellValue(products.get(i).Size);
			sheet.getRow(i+2).createCell(11).setCellValue(products.get(i).Color);
			sheet.getRow(i+2).createCell(12).setCellValue(products.get(i).Color);
			sheet.getRow(i+2).createCell(13).setCellValue(products.get(i).ColorCode);
			sheet.getRow(i+2).createCell(14).setCellValue(products.get(i).Stock);
			sheet.getRow(i+2).createCell(15).setCellValue(products.get(i).published);
			sheet.getRow(i+2).createCell(16).setCellValue(products.get(i).Active);
			sheet.getRow(i+2).createCell(17).setCellValue(products.get(i).shipmentCost);
			sheet.getRow(i+2).createCell(18).setCellValue(products.get(i).CategoryRow);		
		}
		FileOutputStream output = new FileOutputStream(source);
		wb.write(output);
		wb.close();
	}
}

