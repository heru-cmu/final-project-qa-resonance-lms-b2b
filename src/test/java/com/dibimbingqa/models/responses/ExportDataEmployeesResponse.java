package com.dibimbingqa.models.responses;

public class ExportDataEmployeesResponse {
    public Data data;

    public static class Data {
        public String exportDataEmployees;
        // biasanya API return link/file path, bisa String
    }
}
