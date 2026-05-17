package com.dibimbingqa.models.requests;

public class UpdateEmployeesDivisionVariables {
    public String[] employeeIds;
    public String divisionId;

    public UpdateEmployeesDivisionVariables(String[] employeeIds, String divisionId) {
        this.employeeIds = employeeIds;
        this.divisionId = divisionId;
    }
}
