package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WritDataInExcelSheet {

	/*public void WriteInTextFile () {

	//Create File In D: Driver.  
	String TestFile = "D:\\Rania\\Automation Projects\\Scrapping.data\\src\\test\\java\\utilities\\temp.txt";
	File FC = new File(TestFile);//Created object of java File class.
	try {
		FC.createNewFile();
		FileWriter FW = new FileWriter(TestFile);
		BufferedWriter BW = new BufferedWriter(FW);
		BW.write("This Is First Line."); //Writing In To File.
		BW.newLine();//To write next string on new line.
		BW.write("This Is Second Line."); //Writing In To File.
		BW.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//Create file.

	//Writing In to file.
	//Create Object of java FileWriter and BufferedWriter class.

}*/

	public void WriteTextInExcel () throws IOException {

		File source  = new File("D:\\Rania\\Automation Projects\\Scrapping.data\\src\\test\\java\\utilities\\InnerProductData.xlsx");
		FileInputStream input = new FileInputStream(source);
		XSSFWorkbook wb = new XSSFWorkbook(input);
		XSSFSheet sheet = wb.getSheetAt(0);
		sheet.createRow(0).createCell(0).setCellValue("Pass1");
		sheet.getRow(0).createCell(1).setCellValue("Pass2");
		sheet.getRow(0).createCell(2).setCellValue("pass3");
		FileOutputStream output = new FileOutputStream(source);
		wb.write(output);
		wb.close();
	}
}
