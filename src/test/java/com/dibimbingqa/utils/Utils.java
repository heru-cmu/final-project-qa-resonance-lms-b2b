package com.dibimbingqa.utils;

import io.qameta.allure.Allure;

import java.io.FileInputStream;
import java.util.Properties;

public class Utils {
    // 🔹 Print ke console + attach ke Allure
    public static <T> void logResponse(ApiResponse<T> response) {
        System.out.println("🔹 Status Code: " + response.getStatusCode());
        System.out.println("🔹 Headers: " + response.getHeaders());
        System.out.println("🔹 Response Body: " + response.toJsonString());

        // biar muncul di report Allure
        Allure.addAttachment("Response Body", response.toJsonString());
    }
    public static String getProperty(String key) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/test/resources/data.properties"));
            return props.getProperty(key);
        } catch (Exception e) {
            throw new RuntimeException("Property not found: " + key, e);
        }
    }

    public class TestDataStore {
        private static String employeeId;
        private static String divisionId;

        public static void setEmployeeId(String id) { employeeId = id; }
        public static String getEmployeeId() { return employeeId; }

        public static void setDivisionId(String id) { divisionId = id; }
        public static String getDivisionId() { return divisionId; }
    }
}
