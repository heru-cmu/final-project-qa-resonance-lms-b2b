package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.InactivateEmployeeVariables;
import com.dibimbingqa.models.responses.InactivateEmployeeResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class InactivateEmployeeService {
    public static ApiResponse<InactivateEmployeeResponse> inactivateEmployee(String id) {
        String query = TestDataLoader.load("graphql/mutations/InactivateEmployee.graphql");
        InactivateEmployeeVariables variables = new InactivateEmployeeVariables(id);

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(InactivateEmployeeResponse.class)
        );
    }
}
