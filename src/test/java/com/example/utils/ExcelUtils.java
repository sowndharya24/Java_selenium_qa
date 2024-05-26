package com.example.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils(String excelFilePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(excelFilePath);
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
        fis.close();
    }

    public Object[][] getSheetData() {
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                switch (cell.getCellType()) {
                    case STRING:
                        data[i - 1][j] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        data[i - 1][j] = cell.getNumericCellValue();
                        break;
                    case BOOLEAN:
                        data[i - 1][j] = cell.getBooleanCellValue();
                        break;
                    case FORMULA:
                        data[i - 1][j] = cell.getCellFormula();
                        break;
                    default:
                        data[i - 1][j] = "";
                }
            }
        }
        return data;
    }

    public void close() throws IOException {
        workbook.close();
    }
}
