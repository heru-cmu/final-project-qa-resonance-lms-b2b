package com.dibimbingqa.models.responses;

public class ResendEmployeePasswordEmailResponse {
    public Data data;

    public static class Data {
        public Employee resendEmployeePasswordEmail;
    }

    public static class Employee {
        public String id;
        public String __typename;
    }
}
