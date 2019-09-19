package ProcessExcel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alexyou
 * @<version >1.0</version>
 */
public class ReadExcel {

    private static String excelPath= "/Users/alexyou/Desktop/DataToApiTest/DataToAPITest.xlsx";

    /**
     * 读取Excel
     * @return
     */
    public static ArrayList<List> readTestCaseForExcel() {
        XSSFRow xssfRow;
        XSSFCell xssfCell;
        ArrayList allExcelList = new ArrayList();
        try {
            ArrayList columnList;
            FileInputStream fileInputStream = new FileInputStream(new File(excelPath));
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            int columnNum = xssfSheet.getPhysicalNumberOfRows();//获取总列数
            int rowNum = xssfSheet.getRow(0).getPhysicalNumberOfCells();//获取总行数
            System.out.println("rowNum:" + rowNum + "\ncolumnNum:" + columnNum);
            for (int i = 1; i <= columnNum; i++) {
                xssfRow = xssfSheet.getRow(i);
                if (xssfRow == null) {
                    continue;
                }
                columnList = new ArrayList();
                for (int y = 0; y <= rowNum; y++) {
                    xssfCell = xssfRow.getCell(y);
                    if (xssfCell == null) {
                        columnList.add(null);
                        continue;
                    }
                    xssfCell.setCellType(CellType.STRING);
                    String cellValue = xssfCell.getStringCellValue();
                    columnList.add(cellValue);
                }
                allExcelList.add(columnList);
                System.out.println(columnList);

            }
            System.out.println(allExcelList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allExcelList;
    }

    public static void main(String[] args) {
        readTestCaseForExcel();
    }
}
