package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.CreateEmployeeResponse;
import com.dibimbingqa.services.CreateEmployeeService;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateEmployeeTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_001_CreateEmployeeValid() {
        ApiResponse<CreateEmployeeResponse> response = CreateEmployeeService.createEmployee(
                "Umar", "123", "umar@email.com", "85220220220220",
                "00ff9779-4547-48b9-8a9e-ed4522fa6750", "siswa", 1,
                "male", "2026-05-01T00:00:00.000Z", "Bandung", "123456789", "123456789"
        );

        Utils.logResponse(response); // 🔹 otomatis print + attach ke report

        Assert.assertNotNull(response.getResponseBody().data.createEmployee.id,
                "Employee tersimpan");
    }

    @Test
    public void TC_API_EMP_MUT_002_CreateEmployeeInvalidEmail() {
        ApiResponse<CreateEmployeeResponse> response = CreateEmployeeService.createEmployee(
                "Test", "124", "invalidemail.com", "85220220220220",
                "00ff9779-4547-48b9-8a9e-ed4522fa6750", "siswa", 1,
                "male", "2026-05-01T00:00:00.000Z", "Bandung", "123456789", "123456789"
        );

        Utils.logResponse(response); // 🔹 otomatis print + attach ke report

        // Expected error → kalau API tidak error berarti bug
        Assert.assertNull(response.getResponseBody().data.createEmployee.id,
                "Error invalid email");
    }
}
