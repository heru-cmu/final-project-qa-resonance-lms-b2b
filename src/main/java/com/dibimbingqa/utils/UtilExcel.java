package com.dibimbingqa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class UtilExcel {

    public static Object[][] getTestDataFromExcel(String sheetName) {
        Object[][] data = null;
        try (InputStream fis = UtilExcel.class
                .getClassLoader()
                .getResourceAsStream("TestData.xlsx")) {
            if (fis == null) {
                throw new RuntimeException("TestData.xlsx not found in classpath");
            }
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            data = new Object[rowCount - 1][colCount]; // skip header row

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    data[i - 1][j] = getCellValue(cell);
                }
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file", e);
        }
        return data;
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    // format tanggal jadi string dd/MM/yyyy
                    return new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                } else {
                    // angka panjang (NIK/NPWP) jadi string
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return "";
        }
    }
}
