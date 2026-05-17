package com.dibimbingqa.api;

import com.dibimbingqa.utils.ExcelUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import static io.restassured.RestAssured.given;

public class DivisionExportApiTest {
    private static final String BASE_URL = "https://lmsb2b.do.dibimbing.id/graphql";
    private static final String COOKIE = "sid_b2b=" + ExcelUtils.readCookie("ApiCookie");
    private static final String DIVISION_ID = "03a74bdd-7e82-4658-94ae-524160e8dacc"; // hardcode

    @Test(priority = 1)
    public void TC_API_DIV_EXP_001_ExportDataEmployeesValid() {
        String body = "{ \"query\": \"mutation exportDataEmployees($divisionIds:[String!]){ exportDataEmployees(divisionIds:$divisionIds) }\", " +
                "\"operationName\": \"exportDataEmployees\", " +
                "\"variables\": { \"divisionIds\": [\"" + DIVISION_ID + "\"] } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Export employees gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("exportDataEmployees"));
        System.out.println("📂 Export employees per division valid berhasil untuk division ID: " + DIVISION_ID);
    }

    @Test(priority = 2)
    public void TC_API_DIV_EXP_001_NEG_ExportDataEmployeesInvalid() {
        String body = "{ \"query\": \"mutation exportDataEmployees($divisionIds:[String!]){ exportDataEmployees(divisionIds:$divisionIds) }\", " +
                "\"operationName\": \"exportDataEmployees\", " +
                "\"variables\": { \"divisionIds\": [\"INVALID_DIVISION\"] } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        Assert.assertTrue(response.getBody().asString().contains("INVALID_DIVISION"));
        System.out.println("❌ Export employees per division invalid → error: INVALID_DIVISION");
    }
}
