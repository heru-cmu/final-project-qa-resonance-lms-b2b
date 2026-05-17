package com.dibimbingqa.models.responses;

public class CreateEmployeeResponse {
    public Data data;

    public static class Data {
        public CreateEmployee createEmployee;
    }

    public static class CreateEmployee {
        public String id;
        public String __typename;
    }
}
