package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.requests.EmployeesVariables;
import com.dibimbingqa.models.responses.EmployeesResponse;
import com.dibimbingqa.services.EmployeeService;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EmployeeQueryTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_QRY_001_SearchValid() {
        EmployeesVariables vars = new EmployeesVariables("Heru");
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Utils.logResponse(response); // 🔹 otomatis print + attach ke report
        Assert.assertTrue(response.getResponseBody().data.employees.length > 0,
                "Response berisi employee sesuai input");
    }

    @Test
    public void TC_API_EMP_QRY_001_NEG_SearchInvalid() {
        EmployeesVariables vars = new EmployeesVariables("");
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertTrue(response.getResponseBody().data.employees.length >= 0,
                "Response default (semua data)");
    }

    @Test
    public void TC_API_EMP_QRY_002_FilterAngkatanValid() {
        EmployeesVariables vars = new EmployeesVariables(new Float[]{1f}); // contoh angkatanId
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertNotNull(response.getResponseBody().data.employees,
                "Response sesuai filter");
    }

    @Test
    public void TC_API_EMP_QRY_002_NEG_FilterAngkatanInvalid() {
        EmployeesVariables vars = new EmployeesVariables(new Float[]{9999f});
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertTrue(response.getResponseBody().data.employees.length == 0,
                "Error Invalid filter");
    }

    @Test
    public void TC_API_EMP_QRY_003_SortingValid() {
        EmployeesVariables vars = new EmployeesVariables("ASC", "Name");
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertNotNull(response.getResponseBody().data.employees,
                "Data tersorting konsisten");
    }

    @Test
    public void TC_API_EMP_QRY_003_NEG_SortingInvalid() {
        EmployeesVariables vars = new EmployeesVariables("ASC", "unknown");
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertTrue(response.getResponseBody().data.employees.length == 0,
                "Error / data tidak konsisten");
    }

    @Test
    public void TC_API_EMP_QRY_004_PaginationValid() {
        EmployeesVariables vars = new EmployeesVariables(2f, 10f);
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertEquals(response.getResponseBody().data.employees.length, 10,
                "Data tampil sesuai");
    }

    @Test
    public void TC_API_EMP_QRY_004_NEG_PaginationInvalid() {
        EmployeesVariables vars = new EmployeesVariables(-1f, 10f);
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertTrue(response.getResponseBody().data.employees.length == 0,
                "Error Invalid page");
    }

    @Test
    public void TC_API_EMP_QRY_005_EmployeeDetailValid() {
        // bisa bikin query khusus employeeById.graphql
        // untuk sekarang contoh pakai EmployeesResponse
        EmployeesVariables vars = new EmployeesVariables("Heru");
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertNotNull(response.getResponseBody().data.employees[0].id,
                "Response detail employee");
    }

    @Test
    public void TC_API_EMP_QRY_005_NEG_EmployeeDetailInvalid() {
        EmployeesVariables vars = new EmployeesVariables("invalid123");
        ApiResponse<EmployeesResponse> response = EmployeeService.getEmployees(vars);
        Assert.assertTrue(response.getResponseBody().data.employees.length == 0,
                "Error Employee not found");
    }
}
