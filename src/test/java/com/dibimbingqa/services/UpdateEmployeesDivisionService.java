package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.UpdateEmployeesDivisionVariables;
import com.dibimbingqa.models.responses.UpdateEmployeesDivisionResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class UpdateEmployeesDivisionService {
    public static ApiResponse<UpdateEmployeesDivisionResponse> updateEmployeesDivision(
            String[] employeeIds, String divisionId) {

        String query = TestDataLoader.load("graphql/mutations/UpdateEmployeesDivision.graphql");
        UpdateEmployeesDivisionVariables variables = new UpdateEmployeesDivisionVariables(employeeIds, divisionId);

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(UpdateEmployeesDivisionResponse.class)
        );
    }
}
