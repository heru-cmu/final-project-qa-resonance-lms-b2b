package com.dibimbingqa.api;

import com.dibimbingqa.utils.ExcelUtils;
import com.dibimbingqa.utils.TestDataStore;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import static io.restassured.RestAssured.given;

public class DivisionApiTest {
    private static final String BASE_URL = "https://lmsb2b.do.dibimbing.id/graphql";
    private static final String COOKIE = "sid_b2b=" + ExcelUtils.readCookie("ApiCookie");

    @Test(priority = 1)
    public void TC_API_DIV_MUT_001_CreateDivisionValid() {
        String body = "{ \"query\": \"mutation createDivision($input:DivisionInput!){ " +
                "createDivision(input:$input){ id name description __typename }}\", " +
                "\"operationName\": \"createDivision\", " +
                "\"variables\": { \"input\": { " +
                "\"name\": \"Divisi QA\", " +
                "\"description\": \"Quality Assurance\" } } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Create division gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        String divisionId = response.jsonPath().getString("data.createDivision.id");
        Assert.assertNotNull(divisionId, "Division tidak berhasil dibuat!");

        // Simpan ID ke TestDataStore
        TestDataStore.setDivisionId(divisionId);

        System.out.println("📂 Division berhasil dibuat dengan ID: " + divisionId);
    }

    @Test(priority = 2)
    public void TC_API_DIV_MUT_001_NEG_CreateDivisionInvalid() {
        String body = "{ \"query\": \"mutation createDivision($input:DivisionInput!){ " +
                "createDivision(input:$input){ id name description __typename }}\", " +
                "\"operationName\": \"createDivision\", " +
                "\"variables\": { \"input\": { \"name\": \"\", \"description\": \"\" } } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        Assert.assertNotNull(response.jsonPath().getList("errors"), "Seharusnya ada error untuk create division invalid");
    }

    @Test(priority = 3)
    public void TC_API_DIV_MUT_002_UpdateDivisionValid() {
        String divisionId = TestDataStore.getDivisionId();

        String body = "{ \"query\": \"mutation updateDivision($id:String!,$input:DivisionInput!){ " +
                "updateDivision(id:$id,input:$input){ id __typename }}\", " +
                "\"operationName\": \"updateDivision\", " +
                "\"variables\": { \"id\": \"" + divisionId + "\", " +
                "\"input\": { \"name\": \"Update Divisi QA\", \"description\": \"Update Desc\" } } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Update division gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("updateDivision"));
        System.out.println("✏️ Update division dengan ID: " + divisionId);
    }

    @Test(priority = 4)
    public void TC_API_DIV_MUT_002_NEG_UpdateDivisionInvalid() {
        String body = "{ \"query\": \"mutation updateDivision($id:String!,$input:DivisionInput!){ " +
                "updateDivision(id:$id,input:$input){ id __typename }}\", " +
                "\"operationName\": \"updateDivision\", " +
                "\"variables\": { \"id\": \"\", \"input\": { \"name\": \"\", \"description\": \"\" } } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        Assert.assertNotNull(response.jsonPath().getList("errors"), "Seharusnya ada error untuk update division invalid");
    }

    @Test(priority = 5)
    public void TC_API_DIV_MUT_003_DeleteDivisionValid() {
        String divisionId = TestDataStore.getDivisionId();

        String body = "{ \"query\": \"mutation deleteDivision($id:String!){ deleteDivision(id:$id) }\", " +
                "\"operationName\": \"deleteDivision\", " +
                "\"variables\": { \"id\": \"" + divisionId + "\" } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        if (response.jsonPath().getList("errors") != null) {
            Assert.fail("Delete division gagal: " + response.jsonPath().getString("errors[0].message"));
        }

        Assert.assertTrue(response.getBody().asString().contains("deleteDivision"));
        System.out.println("🗑️ Delete division dengan ID: " + divisionId);
    }

    @Test(priority = 6)
    public void TC_API_DIV_MUT_003_NEG_DeleteDivisionInvalid() {
        String body = "{ \"query\": \"mutation deleteDivision($id:String!){ deleteDivision(id:$id) }\", " +
                "\"operationName\": \"deleteDivision\", " +
                "\"variables\": { \"id\": \"INVALID_ID\" } }";

        Response response = given()
                .header("Content-Type","application/json")
                .header("Cookie",COOKIE)
                .body(body)
                .post(BASE_URL);

        Assert.assertNotNull(response.jsonPath().getList("errors"), "Seharusnya ada error untuk delete division invalid");
    }
}
