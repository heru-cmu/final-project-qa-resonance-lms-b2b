package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.ExportDataEmployeesResponse;
import com.dibimbingqa.services.ExportDataEmployeesService;
import com.dibimbingqa.utils.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExportDataEmployeesTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_008_ExportEmployeeValid() {
        ApiResponse<ExportDataEmployeesResponse> response = ExportDataEmployeesService.exportDataEmployees(
                new String[]{"active"}, new String[]{"03a74bdd-7e82-4658-94ae-524160e8dacc"}, ""
        );

        Assert.assertNotNull(response.getResponseBody().data.exportDataEmployees,
                "File CSV sesuai format");
    }

    @Test
    public void TC_API_EMP_MUT_008_NEG_ExportEmployeeInvalid() {
        ApiResponse<ExportDataEmployeesResponse> response = ExportDataEmployeesService.exportDataEmployees(
                new String[]{}, new String[]{"INVALID_DIVISION"}, ""
        );

        Assert.assertNull(response.getResponseBody().data.exportDataEmployees,
                "Error / hasil default");
    }
}
