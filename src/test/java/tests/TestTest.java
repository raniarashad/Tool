package tests;

import java.io.IOException;

import org.testng.annotations.Test;

import pages.test;

public class TestTest extends TestBase{

	test object;
	
	@Test
	public void testt()
	{
		object = new test(driver);
		try {
			object.testOnly();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
