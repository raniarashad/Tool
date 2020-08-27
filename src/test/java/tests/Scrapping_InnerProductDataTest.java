package tests;

import java.io.IOException;

import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.Test;

import pages.Scrapping_InnerProductDataPage;

public class Scrapping_InnerProductDataTest extends TestBase{

	Scrapping_InnerProductDataPage object;

	@Test
	public void Scrapping_InnerProductTest()
	{
		object = new Scrapping_InnerProductDataPage(driver);
		try {
			object.SavingProductImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
