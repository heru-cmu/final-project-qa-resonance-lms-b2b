package com.dibimbingqa.api;

import com.dibimbingqa.utils.ExcelUtils;
import com.dibimbingqa.utils.TestDataStore;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;

public class EmployeeMutationApiTest {
    private static final String BASE_URL = "https://lmsb2b.do.dibimbing.id/graphql";
    private static final String COOKIE = "sid_b2b=" + ExcelUtils.readCookie("ApiCookie");

    @Test(priority = 1)
    public void TC_API_EMP_MUT_001_AddEmployeeValid() {
        String email = "heru" + UUID.randomUUID().toString().substring(0,5) + "@email.com";

        String body = "{ \"query\": \"mutation createEmployee($input: EmployeeInput!){ " +
                "createEmployee(input:$input){ id __typename }}\", " +
                "\"operationName\": \"createEmployee\", " +
                "\"variables\": { \"input\": { " +
                "\"name\": \"Umar\", " +
                "\"employeeId\": \"123\", " +
                "\"email\": \"" + email + "\", " +
                "\"phoneNumber\": \"85220220220220\", " +
                "\"divisionId\": \"00ff9779-4547-48b9-8a9e-ed4522fa6750\", " +
                "\"employeeRole\": \"siswa\", " +
                "\"angkatanId\": 1 } } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Create employee gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        String employeeId = response.jsonPath().getString("data.createEmployee.id");
        Assert.assertNotNull(employeeId, "Employee tidak berhasil dibuat!");

        // Simpan ID ke TestDataStore
        TestDataStore.setEmployeeId(employeeId);

        // 🔹 Print ke console biar puguh
        System.out.println("✅ Employee berhasil dibuat dengan ID: " + employeeId);

    }



    @Test(priority = 2)
    public void TC_API_EMP_MUT_002_AddEmployeeInvalidEmail() {
        String body = "{\"query\":\"mutation createEmployee($input: EmployeeInput!){ createEmployee(input:$input){ id __typename }}\",\"operationName\":\"createEmployee\",\"variables\":{\"input\":{\"name\":\"Test\",\"employeeId\":\"124\",\"email\":\"invalidemail.com\",\"phoneNumber\":\"85220220220220\",\"divisionId\":\"00ff9779-4547-48b9-8a9e-ed4522fa6750\",\"employeeRole\":\"siswa\",\"angkatanId\":1}}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertNotNull(response.jsonPath().getList("errors"), "Seharusnya ada error untuk email invalid");

    }

    @Test(priority = 3)
    public void TC_API_EMP_MUT_002_NEG_AddEmployeeInvalidFormat() {
        String body = "{\"query\":\"mutation createEmployee($input:EmployeeInput!){ createEmployee(input:$input){ id __typename }}\",\"operationName\":\"createEmployee\",\"variables\":{\"input\":{\"name\":\"Test\",\"employeeId\":\"125\",\"email\":\"test@email.com\",\"phoneNumber\":\"ABC123\",\"divisionId\":\"00ff9779-4547-48b9-8a9e-ed4522fa6750\",\"employeeRole\":\"siswa\",\"angkatanId\":1,\"nik\":\"12\",\"npwp\":\"XYZ\"}}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertNotNull(response.jsonPath().getList("errors"), "Seharusnya ada error untuk email invalid");

    }

    @Test(priority = 4)
    public void TC_API_EMP_MUT_003_UpdateEmployeeValid() {
        String employeeId = TestDataStore.getEmployeeId();
        String email = "heruupdated" + UUID.randomUUID().toString().substring(0,5) + "@email.com";

        String body = "{ \"query\": \"mutation updateEmployee($id:String!,$input:EmployeeInput!){ " +
                "updateEmployee(id:$id,input:$input){ id __typename }}\", " +
                "\"operationName\": \"updateEmployee\", " +
                "\"variables\": { \"id\": \"" + employeeId + "\", " +
                "\"input\": { \"name\": \"Updated Name\", \"email\": \"" + email + "\" } } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Update employee gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("updateEmployee"));
        System.out.println("🔄 Update employee dengan ID: " + employeeId + " ke email: " + email);
    }


    @Test(priority = 5)
    public void TC_API_EMP_MUT_003_NEG_UpdateEmployeeInvalid() {
        String body = "{\"query\":\"mutation updateEmployee($id:String!,$input:EmployeeInput!){ updateEmployee(id:$id,input:$input){ id __typename }}\",\"operationName\":\"updateEmployee\",\"variables\":{\"id\":\"\",\"input\":{\"name\":\"coba invalid\",\"email\":\"salah email.ne@\"}}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("invalid"));
    }


    @Test(priority = 6)
    public void TC_API_EMP_MUT_004_NEG_DeleteEmployeeInvalid() {
        String body = "{\"query\":\"mutation deleteEmployee($id:String!){ deleteEmployee(id:$id) }\",\"operationName\":\"deleteEmployee\",\"variables\":{\"id\":\"\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("invalid"));
    }

    @Test(priority = 7)
    public void TC_API_EMP_MUT_005_InactivateEmployeeValid() {
        String employeeId = TestDataStore.getEmployeeId();

        String body = "{ \"query\": \"mutation inactivateEmployee($id:String!){ inactivateEmployee(id:$id) }\", " +
                "\"operationName\": \"inactivateEmployee\", " +
                "\"variables\": { \"id\": \"" + employeeId + "\" } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Inactivate employee gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("inactivateEmployee"));
    }


    @Test(priority = 8)
    public void TC_API_EMP_MUT_005_NEG_InactivateEmployeeInvalid() {
        String body = "{\"query\":\"mutation inactivateEmployee($id:String!){ inactivateEmployee(id:$id) }\",\"operationName\":\"inactivateEmployee\",\"variables\":{\"id\":\"\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("invalid"));
    }

    @Test(priority = 9)
    public void TC_API_EMP_MUT_006_ResendPasswordEmailValid() {
        String employeeId = TestDataStore.getEmployeeId();

        String body = "{ \"query\": \"mutation resendEmployeePasswordEmail($employeeId:String!){ " +
                "resendEmployeePasswordEmail(employeeId:$employeeId){ id __typename }}\", " +
                "\"operationName\": \"resendEmployeePasswordEmail\", " +
                "\"variables\": { \"employeeId\": \"" + employeeId + "\" } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Resend password email gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("resendEmployeePasswordEmail"));
    }


    @Test(priority = 10)
    public void TC_API_EMP_MUT_006_NEG_ResendPasswordEmailInvalid() {
        String body = "{\"query\":\"mutation resendEmployeePasswordEmail($employeeId:String!){ resendEmployeePasswordEmail(employeeId:$employeeId){ id __typename }}\",\"operationName\":\"resendEmployeePasswordEmail\",\"variables\":{\"employeeId\":\"\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("invalid"));
    }

    @Test(priority = 11)
    public void TC_API_EMP_MUT_007_ImportEmployeeValid() {
        // Note: untuk file upload pakai form-data, RestAssured perlu config khusus
        // Bisa diimplementasi nanti sesuai kebutuhan
    }

    @Test(priority = 12)
    public void TC_API_EMP_MUT_007_NEG_ImportEmployeeInvalid() {
        String body = "{\"query\":\"mutation importDataEmployee($file:Upload!){ importDataEmployee(file:$file){ id name email __typename }}\",\"operationName\":\"importDataEmployee\",\"variables\":{\"file\":null}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("must not be null"));
    }

    @Test(priority = 13)
    public void TC_API_EMP_MUT_008_ExportEmployeeValid() {
        String body = "{\"query\":\"mutation exportDataEmployees($status:[String!],$divisionIds:[String!],$search:String){ exportDataEmployees(status:$status,divisionIds:$divisionIds,search:$search) }\",\"operationName\":\"exportDataEmployees\",\"variables\":{\"status\":[],\"divisionIds\":[],\"search\":\"\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("exportDataEmployees"));
    }

    @Test(priority = 14)
    public void TC_API_EMP_MUT_008_NEG_ExportEmployeeInvalid() {
        String body = "{\"query\":\"mutation exportDataEmployees($status:[String!],$divisionIds:[String!],$search:String){ exportDataEmployees(status:$status,divisionIds:$divisionIds,search:$search) }\","
                + "\"operationName\":\"exportDataEmployees\","
                + "\"variables\":{\"status\":[],\"divisionIds\":[\"INVALID_DIVISION\"],\"search\":\"\"}}";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        Assert.assertTrue(response.getBody().asString().contains("INVALID_DIVISION"));
    }

    @Test(priority = 15)
    public void TC_API_EMP_MUT_009_UpdateEmployeesDivisionValid() {
        String employeeId = TestDataStore.getEmployeeId();
        String divisionId = "03a74bdd-7e82-4658-94ae-524160e8dacc"; // hardcode

        String body = "{ \"query\": \"mutation updateEmployeesDivision($employeeIds:[String!]!,$divisionId:String!){ " +
                "updateEmployeesDivision(employeeIds:$employeeIds,divisionId:$divisionId) }\", " +
                "\"operationName\": \"updateEmployeesDivision\", " +
                "\"variables\": { \"employeeIds\": [\"" + employeeId + "\"], " +
                "\"divisionId\": \"" + divisionId + "\" } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Update division gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("updateEmployeesDivision"));
        System.out.println("📂 Update division untuk employee ID: " + employeeId + " ke division ID: " + divisionId);
    }



    @Test(priority = 16)
    public void TC_API_EMP_MUT_009_NEG_UpdateEmployeesDivisionInvalid() {
        String body = "{\"query\":\"mutation updateEmployeesDivision($employeeIds:[String!]!,$divisionId:String!){ updateEmployeesDivision(employeeIds:$employeeIds,divisionId:$divisionId) }\","
                + "\"operationName\":\"updateEmployeesDivision\","
                + "\"variables\":{\"employeeIds\":[],\"divisionId\":null}}";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        Assert.assertTrue(response.getBody().asString().contains("must not be null"));
    }

    @Test(priority = 17)
    public void TC_API_EMP_MUT_004_DeleteEmployeeValid() {
        String employeeId = TestDataStore.getEmployeeId(); // ambil ID dari store

        String body = "{ \"query\": \"mutation deleteEmployee($id:String!){ deleteEmployee(id:$id) }\", " +
                "\"operationName\": \"deleteEmployee\", " +
                "\"variables\": { \"id\": \"" + employeeId + "\" } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Delete employee gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("deleteEmployee"));
        System.out.println("🗑️ Delete employee dengan ID: " + employeeId);
    }


}

