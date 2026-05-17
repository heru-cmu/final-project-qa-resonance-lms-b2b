package com.dibimbingqa.api;

import com.dibimbingqa.utils.ExcelUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.qameta.allure.*;

public class EmployeeQueryApiTest {
    private static final String BASE_URL = "https://lmsb2b.do.dibimbing.id/graphql";
    private static final String COOKIE = "sid_b2b=" + ExcelUtils.readCookie("ApiCookie");

    @Test
    public void TC_API_EMP_QRY_001_SearchValid() {
        String body = "{\"query\":\"query employees($search: String){ employees(search:$search){ id name email __typename }}\",\"operationName\":\"employees\",\"variables\":{\"search\":\"Heru\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("Heru"));
    }

    @Test
    public void TC_API_EMP_QRY_001_NEG_SearchInvalid() {
        String body = "{\"query\":\"query employees($search: String){ employees(search:$search){ id name email __typename }}\",\"operationName\":\"employees\",\"variables\":{\"search\":\"\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("employees"));
    }

    @Test
    public void TC_API_EMP_QRY_002_FilterAngkatanValid() {
        String body = "{\"query\":\"query employees($angkatanIds:[Float!]){ employees(angkatanIds:$angkatanIds){ id name angkatanId __typename }}\",\"operationName\":\"employees\",\"variables\":{\"angkatanIds\":[1]}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("angkatanId"));
    }

    @Test
    public void TC_API_EMP_QRY_002_NEG_FilterAngkatanInvalid() {
        String body = "{\"query\":\"query employees($angkatanIds:[Float!]){ employees(angkatanIds:$angkatanIds){ id name angkatanId __typename }}\",\"operationName\":\"employees\",\"variables\":{\"angkatanIds\":[9999]}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("employees"));
    }

    @Test
    public void TC_API_EMP_QRY_003_SortingValid() {
        String body = "{\"query\":\"query employees($orderBy:String,$orderColumn:String){ employees(orderBy:$orderBy, orderColumn:$orderColumn){ id name __typename }}\",\"operationName\":\"employees\",\"variables\":{\"orderBy\":\"ASC\",\"orderColumn\":\"Name\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("employees"));
    }

    @Test
    public void TC_API_EMP_QRY_003_NEG_SortingInvalid() {
        String body = "{\"query\":\"query employees($orderBy:String,$orderColumn:String){ employees(orderBy:$orderBy, orderColumn:$orderColumn){ id name __typename }}\",\"operationName\":\"employees\",\"variables\":{\"orderBy\":\"ASC\",\"orderColumn\":\"unknown\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("unknown"));
    }

    @Test
    public void TC_API_EMP_QRY_004_PaginationValid() {
        String body = "{\"query\":\"query employees($page:Float,$limit:Float){ employees(page:$page, limit:$limit){ id name __typename }}\",\"operationName\":\"employees\",\"variables\":{\"page\":2,\"limit\":10}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("employees"));
    }

    @Test
    public void TC_API_EMP_QRY_004_NEG_PaginationInvalid() {
        String body = "{\"query\":\"query employees($page:Float,$limit:Float){ employees(page:$page, limit:$limit){ id name __typename }}\",\"operationName\":\"employees\",\"variables\":{\"page\":-1,\"limit\":10}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("OFFSET"));
    }

    @Test
    public void TC_API_EMP_QRY_005_EmployeeDetailValid() {
        String body = "{\"query\":\"query employeeById($id:String!){ employeeById(id:$id){ id name email __typename }}\",\"operationName\":\"employeeById\",\"variables\":{\"id\":\"56d31234-cf3b-4713-90bd-24869a8db273\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("employeeById"));
    }

    @Test
    public void TC_API_EMP_QRY_005_NEG_EmployeeDetailInvalid() {
        String body = "{\"query\":\"query employeeById($id:String!){ employeeById(id:$id){ id name email __typename }}\",\"operationName\":\"employeeById\",\"variables\":{\"id\":\"invalid123\"}}";
        Response response = given().header("Content-Type","application/json").header("Cookie",COOKIE).body(body).post(BASE_URL);
        Assert.assertTrue(response.getBody().asString().contains("invalid"));
    }
}
