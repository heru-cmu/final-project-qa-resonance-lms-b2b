package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.DeleteEmployeeResponse;
import com.dibimbingqa.services.DeleteEmployeeService;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteEmployeeTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_004_DeleteEmployeeValid() {
        ApiResponse<DeleteEmployeeResponse> response = DeleteEmployeeService.deleteEmployee(
                "36d21a19-6dad-40ae-b61b-06f264b8d55f"
        );

        Utils.logResponse(response); // 🔹 otomatis print + attach ke report

        Assert.assertTrue(response.getResponseBody().data.deleteEmployee,
                "Employee terhapus");
    }

    @Test
    public void TC_API_EMP_MUT_004_NEG_DeleteEmployeeInvalid() {
        ApiResponse<DeleteEmployeeResponse> response = DeleteEmployeeService.deleteEmployee(
                "INVALID_ID"
        );

        Utils.logResponse(response); // 🔹 otomatis print + attach ke report

        // Expected error → kalau API tidak error berarti bug
        Assert.assertFalse(response.getResponseBody().data.deleteEmployee,
                "Error / tidak ada request");
    }
}
