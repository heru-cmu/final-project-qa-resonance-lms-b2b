package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.UpdateEmployeeResponse;
import com.dibimbingqa.services.UpdateEmployeeService;
import com.dibimbingqa.utils.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateEmployeeTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_003_UpdateEmployeeValid() {
        ApiResponse<UpdateEmployeeResponse> response = UpdateEmployeeService.updateEmployee(
                "acc62af9-aa13-46b5-a7fa-7191cc2a1398", "Updated Name", "updated@email.com"
        );

        Assert.assertNotNull(response.getResponseBody().data.updateEmployee.id,
                "Data tersimpan");
    }

    @Test
    public void TC_API_EMP_MUT_003_NEG_UpdateEmployeeInvalid() {
        ApiResponse<UpdateEmployeeResponse> response = UpdateEmployeeService.updateEmployee(
                "", "coba invalid", "salah email.ne@"
        );

        // Expected error → kalau API tidak error berarti bug
        Assert.assertNull(response.getResponseBody().data.updateEmployee.id,
                "Error Required / invalid format");
    }
}
