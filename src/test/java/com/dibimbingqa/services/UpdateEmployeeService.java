package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.UpdateEmployeeVariables;
import com.dibimbingqa.models.responses.UpdateEmployeeResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class UpdateEmployeeService {
    public static ApiResponse<UpdateEmployeeResponse> updateEmployee(String id, String name, String email) {
        String query = TestDataLoader.load("graphql/mutations/UpdateEmployee.graphql");
        UpdateEmployeeVariables variables = new UpdateEmployeeVariables(id, name, email);

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(UpdateEmployeeResponse.class)
        );
    }
}
