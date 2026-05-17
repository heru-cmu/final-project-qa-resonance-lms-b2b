package com.dibimbingqa.models.responses;

public class EmployeesResponse {
    public Data data;

    public static class Data {
        public Employee[] employees;
    }

    public static class Employee {
        public String id;
        public String name;
        public String email;
        public Integer angkatanId;
    }
}
