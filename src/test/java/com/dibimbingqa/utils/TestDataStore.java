package com.dibimbingqa.utils;

public class TestDataStore {
    private static String employeeId;
    private static String divisionId;

    public static void setEmployeeId(String id) { employeeId = id; }
    public static String getEmployeeId() { return employeeId; }

    public static void setDivisionId(String id) { divisionId = id; }
    public static String getDivisionId() { return divisionId; }
}
