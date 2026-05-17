package com.dibimbingqa.services;

import com.dibimbingqa.models.requests.DeleteEmployeeVariables;
import com.dibimbingqa.models.responses.DeleteEmployeeResponse;
import com.dibimbingqa.utils.ApiResponse;
import com.dibimbingqa.utils.TestDataLoader;
import com.dibimbingqa.client.GraphQlClient;
import io.restassured.response.Response;

public class DeleteEmployeeService {
    public static ApiResponse<DeleteEmployeeResponse> deleteEmployee(String id) {
        String query = TestDataLoader.load("graphql/mutations/DeleteEmployee.graphql");
        DeleteEmployeeVariables variables = new DeleteEmployeeVariables(id);

        Response response = GraphQlClient.execute(query, variables);

        return new ApiResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                response.as(DeleteEmployeeResponse.class)
        );
    }
}
