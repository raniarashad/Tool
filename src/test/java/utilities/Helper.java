package utilities;


import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Helper {

	// Method take screen shot when the test case is fail
	
	public static void CaptureScreenShot (WebDriver driver , String screenshotname)
	{
		// screen shot name will be as Testcase name
		Path destination = Paths.get("./ScreenShots" , screenshotname +".png");
		try {
			Files.createDirectories(destination.getParent());
			FileOutputStream out = new FileOutputStream(destination.toString());
			out.write(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES));
			out.close();
			
		} catch (IOException e) {
			System.out.println("Exception while taking sceenshot" + e.getMessage());
		}
	}
	
}
