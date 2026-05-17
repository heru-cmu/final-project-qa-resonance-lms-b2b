package com.dibimbingqa.models.requests;

public class EmployeesVariables {
    public String search;
    public Float[] angkatanIds;
    public String orderBy;
    public String orderColumn;
    public Float page;
    public Float limit;

    public EmployeesVariables(String search) {
        this.search = search;
    }

    public EmployeesVariables(Float[] angkatanIds) {
        this.angkatanIds = angkatanIds;
    }

    public EmployeesVariables(String orderBy, String orderColumn) {
        this.orderBy = orderBy;
        this.orderColumn = orderColumn;
    }

    public EmployeesVariables(Float page, Float limit) {
        this.page = page;
        this.limit = limit;
    }
}
