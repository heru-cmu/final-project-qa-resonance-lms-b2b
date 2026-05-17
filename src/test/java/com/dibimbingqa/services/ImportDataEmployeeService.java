package com.dibimbingqa.services;

import com.dibimbingqa.models.responses.ImportDataEmployeeResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ImportDataEmployeeService {
    public static ApiResponse<ImportDataEmployeeResponse> importDataEmployee(File csvFile) {
        String query = TestDataLoader.load("graphql/mutations/ImportDataEmployee.graphql");

        // GraphQL multipart upload requires operations + map + file
        String operations = "{\"query\":\"" + query.replace("\n", " ") + "\",\"variables\":{\"file\":null}}";
        String map = "{\"0\":[\"variables.file\"]}";

        Response response = given()
                .baseUri(System.getenv("BASE_URL"))
                .contentType(ContentType.MULTIPART)
                .cookie("sid_b2b", com.dibimbingqa.client.AuthSession.getSessionCookie())
                .multiPart("operations", operations)
                .multiPart("map", map)
                .multiPart("0", csvFile)
                .post("/graphql")
                .then()
                .extract()
                .response();

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(ImportDataEmployeeResponse.class)
        );
    }
}
