package tests;

import java.io.IOException;

import org.testng.annotations.Test;

import pages.Testpage;
import pages.test;

public class TestTest extends TestBase{

	Testpage object;
	
	@Test
	public void testt() throws IOException
	{
		object = new Testpage(driver);
		object.Test();
	}
}
