package com.dibimbingqa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtils {

    private static final String FILE_PATH = "src/test/resources/TestData.xlsx";

    // Simpan cookie ke sheet ApiCookie
    public static void writeCookie(String sheetName, String sessionToken) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            Row row = sheet.getRow(0);
            if (row == null) {
                row = sheet.createRow(0);
            }

            Cell cellA = row.createCell(0);
            cellA.setCellValue("sessionToken");

            Cell cellB = row.createCell(1);
            cellB.setCellValue(sessionToken);

            fis.close();

            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Cookie disimpan ke Excel sheet: " + sheetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Baca cookie dari sheet ApiCookie
    public static String readCookie(String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(FILE_PATH);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(0);
            String sessionToken = row.getCell(1).getStringCellValue();

            fis.close();
            workbook.close();

            return sessionToken;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
