package genericutilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils(String filePath, String sheetName) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in the Excel file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the Excel file: " + filePath, e);
        }
    }

    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            throw new RuntimeException("Row " + rowNum + " does not exist in the sheet.");
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            throw new RuntimeException("Cell at row " + rowNum + " and column " + colNum + " does not exist.");
        }
        return cell.getStringCellValue();
    }

    public int getRowCount() {
        if (sheet == null) {
            throw new RuntimeException("Sheet is not initialized.");
        }
        return sheet.getPhysicalNumberOfRows();
    }
}
