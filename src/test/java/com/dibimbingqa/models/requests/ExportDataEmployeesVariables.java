package com.dibimbingqa.models.requests;

public class ExportDataEmployeesVariables {
    public String[] status;
    public String[] divisionIds;
    public String search;

    public ExportDataEmployeesVariables(String[] status, String[] divisionIds, String search) {
        this.status = status;
        this.divisionIds = divisionIds;
        this.search = search;
    }
}
