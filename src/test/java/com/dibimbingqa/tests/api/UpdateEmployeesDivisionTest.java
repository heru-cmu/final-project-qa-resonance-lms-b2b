package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.UpdateEmployeesDivisionResponse;
import com.dibimbingqa.services.UpdateEmployeesDivisionService;
import com.dibimbingqa.utils.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UpdateEmployeesDivisionTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_009_UpdateEmployeesDivisionValid() {
        ApiResponse<UpdateEmployeesDivisionResponse> response =
                UpdateEmployeesDivisionService.updateEmployeesDivision(
                        new String[]{"acc62af9-aa13-46b5-a7fa-7191cc2a1398"},
                        "03a74bdd-7e82-4658-94ae-524160e8dacc"
                );

        Assert.assertTrue(response.getResponseBody().data.updateEmployeesDivision,
                "Employee berpindah division");
    }

    @Test
    public void TC_API_EMP_MUT_009_NEG_UpdateEmployeesDivisionInvalid() {
        ApiResponse<UpdateEmployeesDivisionResponse> response =
                UpdateEmployeesDivisionService.updateEmployeesDivision(
                        new String[]{}, null
                );

        Assert.assertFalse(response.getResponseBody().data.updateEmployeesDivision,
                "Error / tidak ada request");
    }
}
