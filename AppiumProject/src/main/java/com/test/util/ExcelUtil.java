package com.test.util;

import com.test.config.ConstantParameters;
import com.test.script.TestApp;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class ExcelUtil {
    private static Workbook wookBook;

    /*
     * Open Excel file.
     */
    public static void setExcelFile(String path) {
        FileInputStream excelFile;

        try {
            excelFile = new FileInputStream(path);
            String fileExtensionName = path.substring(path.indexOf("."));
            if (fileExtensionName.equals(".xlsx")) {
                wookBook = new XSSFWorkbook(excelFile);
            } else if (fileExtensionName.equals(".xls")) {
                wookBook = new HSSFWorkbook(excelFile);
            }
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * Get total rows in the sheet
     */
    public static int getRowCount(String sheetName) {
        Sheet excelSheet = wookBook.getSheet(sheetName);
        return excelSheet.getLastRowNum();
    }

    /*
     * Get the first test step number
     */
    public static int getFirstStepRowNum(String sheetName, String testCaseName, int colNum) {
        int rowCount = getRowCount(sheetName);
        for (int i=0; i<=rowCount; i++) {
            if (getCellData(sheetName, i, colNum).equalsIgnoreCase(testCaseName)) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Get the last test step number
     */
    public static int getLastStepRowNum(String sheetName, String testCaseID, int testCaseStartRowNum) {
        for (int i=testCaseStartRowNum; i<=getRowCount(sheetName); i++) {
            if (!testCaseID.equals(getCellData(sheetName, i, ConstantParameters.testCaseIdCol))) {
                return i-1;
            }
        }
        return getRowCount(sheetName);
    }

    /*
     * Write data.
     * Here means test result, Pass or Fail.
     */
    public static void setCellData(String sheetName, int rowNum, int colNum, String result) {
        try {
            Sheet excelSheet = wookBook.getSheet(sheetName);
            Row row = excelSheet.getRow(rowNum);
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(result);
            CellStyle cellStyle = wookBook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            if (result.equals("Pass")) {
                cellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            } else if(result.equals("Fail")) {
                cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            } else {
                cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            }
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(cellStyle);

            FileOutputStream fileOut = new FileOutputStream(ConstantParameters.testPlanPath);
            wookBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
        }
    }

    /*
     * Read data.
     */
    public static String getCellData(String sheetName, int rowNum, int colNum) {
        try {
            Sheet excelSheet = wookBook.getSheet(sheetName);
            Cell cell = excelSheet.getRow(rowNum).getCell(colNum);
            String cellData = null;
            if (cell.getCellType() == CellType.NUMERIC) {
                DecimalFormat df = new DecimalFormat("#");
                cellData = df.format(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.STRING) {
                cellData = cell.getStringCellValue();
            }
            return cellData;

        } catch (Exception e) {
            TestApp.setTestResult(false);
            return null;
        }
    }
}
