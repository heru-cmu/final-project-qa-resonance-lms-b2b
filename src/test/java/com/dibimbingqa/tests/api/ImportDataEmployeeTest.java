package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.ImportDataEmployeeResponse;
import com.dibimbingqa.services.ImportDataEmployeeService;
import com.dibimbingqa.utils.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class ImportDataEmployeeTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_007_ImportEmployeeValid() {
        File csvFile = new File("src/test/resources/data/employees_valid.csv");
        ApiResponse<ImportDataEmployeeResponse> response = ImportDataEmployeeService.importDataEmployee(csvFile);

        Assert.assertTrue(response.getResponseBody().data.importDataEmployee.length > 0,
                "Data tersimpan dari file CSV");
    }

    @Test
    public void TC_API_EMP_MUT_007_NEG_ImportEmployeeInvalid() {
        File csvFile = new File("src/test/resources/data/employees_invalid.csv");
        ApiResponse<ImportDataEmployeeResponse> response = ImportDataEmployeeService.importDataEmployee(csvFile);

        Assert.assertTrue(response.getResponseBody().data.importDataEmployee == null
                        || response.getResponseBody().data.importDataEmployee.length == 0,
                "Error sesuai aturan (file kosong/format salah)");
    }
}
