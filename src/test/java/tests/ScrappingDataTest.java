package tests;

import java.io.IOException;

import org.testng.annotations.Test;

import pages.ScrappingDataPage;
public class ScrappingDataTest extends TestBase{

	ScrappingDataPage Object;
	
	@Test
	public void Scrapping ()
	{
		Object = new ScrappingDataPage(driver);
		try {
			Object.ScrappingInnerProductURL();
			Object.Open_Href_FromList();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
