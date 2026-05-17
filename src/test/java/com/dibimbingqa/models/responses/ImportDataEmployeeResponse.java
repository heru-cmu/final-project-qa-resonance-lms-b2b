package com.dibimbingqa.models.responses;

public class ImportDataEmployeeResponse {
    public Data data;

    public static class Data {
        public Employee[] importDataEmployee;
    }

    public static class Employee {
        public String id;
        public String name;
        public String email;
    }
}
