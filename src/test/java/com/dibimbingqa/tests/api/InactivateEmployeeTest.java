package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.InactivateEmployeeResponse;
import com.dibimbingqa.services.InactivateEmployeeService;
import com.dibimbingqa.utils.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InactivateEmployeeTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_005_InactivateEmployeeValid() {
        ApiResponse<InactivateEmployeeResponse> response = InactivateEmployeeService.inactivateEmployee(
                "acc62af9-aa13-46b5-a7fa-7191cc2a1398"
        );

        Assert.assertTrue(response.getResponseBody().data.inactivateEmployee,
                "Status berubah");
    }

    @Test
    public void TC_API_EMP_MUT_005_NEG_InactivateEmployeeInvalid() {
        ApiResponse<InactivateEmployeeResponse> response = InactivateEmployeeService.inactivateEmployee(
                "INVALID_ID"
        );

        Assert.assertFalse(response.getResponseBody().data.inactivateEmployee,
                "Error / tidak ada request");
    }
}
