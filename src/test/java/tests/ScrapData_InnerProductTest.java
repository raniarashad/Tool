package tests;

import pages.ScrapData_InnerProductPage;

import java.io.IOException;

import org.testng.annotations.*;

public class ScrapData_InnerProductTest extends TestBase {

	ScrapData_InnerProductPage HomeObject;

	@Test
	public void Test()
	{

		HomeObject = new ScrapData_InnerProductPage(driver);

		try {
			//HomeObject.WriteTextInExcel();
			 HomeObject.ScrappingInnerProductData();
			  //HomeObject.ScrappingInnerProductURL();
			 // HomeObject.Open_Href_FromList();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
