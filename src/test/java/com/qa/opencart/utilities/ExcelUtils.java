package com.qa.opencart.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    public FileInputStream fis = null;
    public FileOutputStream fileOut = null;
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    /**
     * this Method is used to read the data from excelsheets
     * @param path
     * @param sheetName
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public Object[][] getTestData(String path,String sheetName) throws InvalidFormatException, IOException {
        ChainTestListener.log("Specify the path of file :"+path);
        File srcFile=new File(path);
        ChainTestListener.log("read the file");
        fis=new FileInputStream(srcFile);
        ChainTestListener.log("Load workbook");
        workbook=new XSSFWorkbook(fis);
        //Load sheet- Here we are loading first sheetonly
        sheet= workbook.getSheet(sheetName);
        ChainTestListener.log("fetch the row count");	//two d array declaration
        int rowCount=sheet.getLastRowNum();
        ChainTestListener.log("number of rows in the excel sheet is-->"+rowCount);
        int colCount=sheet.getRow(0).getLastCellNum();
        ChainTestListener.log("number of columns in the excel sheet is -->"+colCount);
        Object[][] data = new Object[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int k = 0; k < colCount; k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
            }
        }
        return data;
    }
}