package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.ExportDataEmployeesVariables;
import com.dibimbingqa.models.responses.ExportDataEmployeesResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class ExportDataEmployeesService {
    public static ApiResponse<ExportDataEmployeesResponse> exportDataEmployees(
            String[] status, String[] divisionIds, String search) {

        String query = TestDataLoader.load("graphql/mutations/ExportDataEmployees.graphql");
        ExportDataEmployeesVariables variables = new ExportDataEmployeesVariables(status, divisionIds, search);

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(ExportDataEmployeesResponse.class)
        );
    }
}
