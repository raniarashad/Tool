package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;



import utilities.Helper;

public class TestBase {

	public static WebDriver driver;


	@BeforeSuite
	@Parameters({"browser"})
	public void StartDriver (@Optional("chrome") String browserName) throws IOException
	{
		// ignorecase -- ignore case from name B or b
		if (browserName.equalsIgnoreCase("chrome"))
		{
			String path = System.getProperty("user.dir");   // return project folder path
			String driverpath = path + "\\Drivers\\chromedriver.exe";   // return driver folder path 
			System.setProperty("webdriver.chrome.driver",driverpath );
			driver = new ChromeDriver();
		}

		else if (browserName.equalsIgnoreCase("Firefox"))
		{
			String path = System.getProperty("user.dir");   // return project folder path
			String driverpath = path + "\\Drivers\\geckodriver.exe";   // return driver folder path 
			System.setProperty("webdriver.gecko.driver",driverpath );
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please Enter the required URL : ");
		String URL = reader.readLine();
		driver.navigate().to(URL);
	}

	@AfterSuite(enabled = false)
	public void StopDriver()
	{
		driver.quit();
	}

	// Take screen shot when testcase fail and add it in the screenshots folder 
	@AfterMethod 
	public void ScreenShot (ITestResult result)
	{
		if (result.getStatus() == ITestResult.FAILURE)
		{
			System.out.println("Failed!");
			System.out.println("Taking Failed screenshot......");
			Helper.CaptureScreenShot(driver, result.getName());
		}

	}

}
